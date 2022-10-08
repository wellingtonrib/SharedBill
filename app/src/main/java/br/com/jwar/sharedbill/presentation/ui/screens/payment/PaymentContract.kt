package br.com.jwar.sharedbill.presentation.ui.screens.payment

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import java.util.Date

class PaymentContract {

    sealed class Event: UiEvent {
        class OnRequestGroup(val groupId: String): Event()
        class SendPayment(val params: SendPaymentParams) : Event()

        class SendPaymentParams(
            val description: String,
            val value: String,
            val paidBy: User?,
            val paidTo: List<User>,
            val date: Date,
            val group: Group
        )
    }

    sealed class State: UiState {
        object Loading: State()
        class Editing(val group: Group): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object Finish: Effect()
        class ShowError(val message: String): Effect()
    }

}