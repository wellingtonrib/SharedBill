package br.com.jwar.sharedbill.presentation.ui.screens.payment

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdWithCurrentMemberUseCase
import br.com.jwar.sharedbill.domain.usecases.SendPaymentUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val getGroupByIdWithCurrentMemberUseCase: GetGroupByIdWithCurrentMemberUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnRequestGroup -> onRequestGroup(event.groupId)
            is Event.SendPayment -> onSendPayment(event.params)
        }
    }

    private fun onSendPayment(params: Event.SendPaymentParams) = viewModelScope.launch {
        sendPaymentUseCase(params).collect { resource ->
            when(resource) {
                is Resource.Loading -> setState { State.Loading }
                is Resource.Success -> sendEffect { Effect.Finish }
                is Resource.Failure -> {
                    setState { State.Editing(params.group, params.currentMember) }
                    sendEffect { Effect.ShowError(resource.throwable.message.orEmpty()) }
                }
            }
        }
    }

    private fun onRequestGroup(groupId: String) = viewModelScope.launch {
        getGroupByIdWithCurrentMemberUseCase(groupId, true).collect { resource ->
            when(resource) {
                is Resource.Loading -> setState { State.Loading }
                is Resource.Success -> setState { State.Editing(resource.data.first, resource.data.second) }
                is Resource.Failure -> setState { State.Error(resource.throwable.message.orEmpty()) }
            }
        }
    }
}