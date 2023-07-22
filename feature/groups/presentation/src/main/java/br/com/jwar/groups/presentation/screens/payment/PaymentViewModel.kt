package br.com.jwar.groups.presentation.screens.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.groups.presentation.mappers.UserToGroupMemberUiModelMapper
import br.com.jwar.groups.presentation.models.PaymentType
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.navigation.GROUP_ID_ARG
import br.com.jwar.groups.presentation.navigation.PAYMENT_TYPE_ARG
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
    private val paymentType = PaymentType.from(checkNotNull(savedStateHandle[PAYMENT_TYPE_ARG]))

    init { onInit(groupId, paymentType) }

    override fun getInitialState(): State = State(isLoading = true)

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnSavePayment -> onSavePayment()
            is Event.OnParamsChange -> setPaymentParams(event.params)
        }
    }

    private fun onInit(groupId: String, paymentType: PaymentType) = viewModelScope.launch {
        setLoadingState()
        getGroupByIdUseCase(groupId, false)
            .onSuccess { group -> setPaymentParams(getPaymentParams(group, paymentType)) }
            .onFailure { handlePaymentError(it) }
    }

    private fun onSavePayment() = viewModelScope.launch {
        setLoadingState()
        with(getCurrentPaymentParams()) {
            createPaymentUseCase(
                description = description,
                value = value,
                date = date,
                paidById = paidBy.uid,
                paidToIds = paidTo.map { it.uid },
                groupId = group.id
            ).onSuccess { onPaymentCreated(it) }
                .onFailure { handlePaymentError(it) }
        }
    }

    private fun onPaymentCreated(payment: Payment) = viewModelScope.launch {
        setLoadingState()
        sendPaymentUseCase(payment)
            .onSuccess { sendFinishEffect() }
            .onFailure { handlePaymentError(it) }
    }

    private fun getPaymentParams(group: Group, paymentType: PaymentType): PaymentContract.PaymentParams =
        groupToGroupUiModelMapper.mapFrom(group).let { groupUiModel ->
            val paidBy = group.findCurrentUser() ?: group.owner
            PaymentContract.PaymentParams(
                group = groupUiModel,
                paidBy = userToGroupMemberUiModelMapper.mapFrom(paidBy),
                paidTo = groupUiModel.members,
                paymentType = paymentType
            )
        }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun setPaymentParams(params: PaymentContract.PaymentParams) =
        setState { it.copy(isLoading = false, params = params) }

    private fun getCurrentPaymentParams() = uiState.value.params ?: PaymentContract.PaymentParams()

    private fun handlePaymentError(throwable: Throwable) =
        setState {
            val paymentError = PaymentUiError.mapFrom(throwable)
            val params = it.params?.copy(error = paymentError)
            it.copy(isLoading = false, params = params)
        }

    private fun sendFinishEffect() = sendEffect { Effect.Finish }
}