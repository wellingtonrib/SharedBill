package br.com.jwar.groups.presentation.screens.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.groups.presentation.mappers.UserToGroupMemberUiModelMapper
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.navigation.GROUP_ID_ARG
import br.com.jwar.groups.presentation.screens.payment.PaymentContract.Effect
import br.com.jwar.groups.presentation.screens.payment.PaymentContract.Event
import br.com.jwar.groups.presentation.screens.payment.PaymentContract.State
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.usecases.CreatePaymentUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.SendPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PaymentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val createPaymentUseCase: CreatePaymentUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
    private val userToGroupMemberUiModelMapper: UserToGroupMemberUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    private val groupId: String = checkNotNull(savedStateHandle[GROUP_ID_ARG])

    init { onInit(groupId) }

    override fun getInitialState(): State = State(isLoading = true)

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnCreatePayment -> onCreatePayment()
            is Event.OnParamsChange -> setPaymentParams(event.params)
        }
    }

    private fun onInit(groupId: String) = viewModelScope.launch {
        setLoadingState()
        getGroupByIdUseCase(groupId, false)
            .onSuccess { setPaymentParams(getPaymentParams(it)) }
            .onFailure { handlePaymentError(it) }
    }

    private fun onCreatePayment() = viewModelScope.launch {
        setLoadingState()
        with(getCurrentPaymentParams()) {
            createPaymentUseCase(
                description = description,
                value = value,
                date = date,
                paidById = paidBy.uid,
                paidToIds = paidTo.map { it.uid },
                groupId = group.id
            ).onSuccess { onSendPayment(it) }
                .onFailure { handlePaymentError(it) }
        }
    }

    private fun onSendPayment(payment: Payment) = viewModelScope.launch {
        setLoadingState()
        sendPaymentUseCase(payment)
            .onSuccess { sendFinishEffect() }
            .onFailure { handlePaymentError(it) }
    }

    private fun getPaymentParams(group: Group): PaymentContract.PaymentParams =
        groupToGroupUiModelMapper.mapFrom(group).let { groupUiModel ->
            PaymentContract.PaymentParams(
                group = groupUiModel,
                paidBy = userToGroupMemberUiModelMapper.mapFrom(
                    group.findCurrentUser() ?: group.owner
                ),
                paidTo = groupUiModel.members
            )
        }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun setPaymentParams(params: PaymentContract.PaymentParams) =
        setState { it.copy(isLoading = false, params = params) }

    private fun getCurrentPaymentParams() = uiState.value.params ?: PaymentContract.PaymentParams()

    private fun handlePaymentError(throwable: Throwable) {
        val paymentError = PaymentUiError.mapFrom(throwable)
        setState {
            it.copy(isLoading = false, params = it.params?.copy(error = paymentError))
        }
    }

    private fun sendFinishEffect() = sendEffect { Effect.Finish }
}