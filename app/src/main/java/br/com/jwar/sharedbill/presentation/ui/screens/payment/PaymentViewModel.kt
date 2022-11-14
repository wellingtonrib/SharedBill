package br.com.jwar.sharedbill.presentation.ui.screens.payment

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdWithCurrentMemberUseCase
import br.com.jwar.sharedbill.domain.usecases.GetPaymentParamsUseCase
import br.com.jwar.sharedbill.domain.usecases.SendPaymentUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.presentation.ui.util.UiText
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val getGroupByIdWithCurrentMemberUseCase: GetGroupByIdWithCurrentMemberUseCase,
    private val getPaymentParamsUseCase: GetPaymentParamsUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnRequestGroup -> onRequestGroup(event.groupId)
            is Event.SendPayment -> onSendPayment()
            is Event.OnPaymentParamsChange -> onPaymentParamsChange(event.params)
        }
    }

    private fun onSendPayment() = viewModelScope.launch {
        val params = getEditingPaymentParams() ?: return@launch
        sendPaymentUseCase(params).collect { resource ->
            when(resource) {
                is Resource.Loading -> setLoadingState()
                is Resource.Success -> sendFinishEffect()
                is Resource.Failure -> handlePaymentError(params, resource.throwable)
            }
        }
    }

    private fun onRequestGroup(groupId: String) = viewModelScope.launch {
        getGroupByIdWithCurrentMemberUseCase(groupId).collect { resource ->
            when(resource) {
                is Resource.Loading -> setLoadingState()
                is Resource.Success -> setEditingState(resource.data.first, resource.data.second)
                is Resource.Failure -> setErrorState(resource.throwable)
            }
        }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setEditingState(group: Group, user: User) =
        setState {
            State.Editing(getPaymentParamsUseCase(group, user))
        }

    private fun setEditingState(params: SendPaymentParams) = setState { State.Editing(params) }

    private fun setErrorState(throwable: Throwable) =
        setState { State.Error(throwable.message.orEmpty()) }

    private fun sendErrorEffect(uiText: UiText) =
        sendEffect { Effect.ShowError(uiText) }

    private fun handlePaymentError(params: SendPaymentParams, throwable: Throwable) {
        val paymentError = PaymentUiError.mapFrom(throwable)
        setEditingState(params.copy(error = paymentError))
        sendErrorEffect(paymentError.message)
    }

    private fun sendFinishEffect() = sendEffect { Effect.Finish }

    private fun onPaymentParamsChange(params: SendPaymentParams) =
        setState { State.Editing(params) }

    private fun getEditingPaymentParams() = (uiState.value as? State.Editing)?.params
}