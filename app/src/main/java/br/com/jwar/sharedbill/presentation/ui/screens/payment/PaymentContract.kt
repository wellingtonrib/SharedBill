package br.com.jwar.sharedbill.presentation.ui.screens.payment

import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import java.util.Date

class PaymentContract {

    sealed class Event: UiEvent {
        class OnRequestGroup(val groupId: String): Event()
        class SendPayment(val params: SendPaymentParams) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Editing(val params: SendPaymentParams): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object Finish: Effect()
        class ShowError(val message: String): Effect()
    }

    data class SendPaymentParams(
        val description: String = "",
        val value: String = "",
        val paidBy: UserUiModel,
        val paidTo: List<UserUiModel> = emptyList(),
        val date: Date = Date(),
        val group: GroupUiModel,
    )
}