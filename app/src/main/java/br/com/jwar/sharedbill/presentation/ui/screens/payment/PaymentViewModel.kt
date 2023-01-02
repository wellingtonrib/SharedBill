package br.com.jwar.sharedbill.presentation.ui.screens.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.usecases.CreatePaymentUseCase
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.domain.usecases.SendPaymentUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.presentation.navigation.AppDestinationsArgs
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val createPaymentUseCase: CreatePaymentUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
    private val userToUserUiModelMapper: UserToUserUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    private val groupId: String = checkNotNull(savedStateHandle[AppDestinationsArgs.GROUP_ID_ARG])

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
        createPaymentUseCase(getCurrentPaymentParams())
            .onSuccess { onSendPayment(it) }
            .onFailure { handlePaymentError(it) }
    }

    private fun onSendPayment(payment: Payment) = viewModelScope.launch {
        setLoadingState()
        sendPaymentUseCase(payment)
            .onSuccess { sendFinishEffect() }
            .onFailure { handlePaymentError(it) }
    }

    private fun getPaymentParams(group: Group): PaymentParams =
        groupToGroupUiModelMapper.mapFrom(group).let { groupUiModel ->
            PaymentParams(
                group = groupUiModel,
                paidBy = userToUserUiModelMapper.mapFrom(group.findCurrentUser() ?: group.owner),
                paidTo = groupUiModel.members
            )
        }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun setPaymentParams(params: PaymentParams) =
        setState { it.copy(isLoading = false, params = params) }

    private fun getCurrentPaymentParams() = uiState.value.params ?: PaymentParams()

    private fun handlePaymentError(throwable: Throwable) {
        val paymentError = PaymentUiError.mapFrom(throwable)
        setState { it.copy(isLoading = false, params = it.params?.copy(error = paymentError)) }
        sendEffect { Effect.ShowError(paymentError.message) }
    }

    private fun sendFinishEffect() = sendEffect { Effect.Finish }
}