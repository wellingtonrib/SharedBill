package br.com.jwar.groups.presentation.ui.payment

import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState

class PaymentContract {

    sealed class Event: UiEvent {
        data class OnSavePayment(val payment: PaymentUiModel) : Event()
    }

    sealed class State(
    ): UiState {
        object Loading: State()
        data class Loaded(
            val group: GroupUiModel,
            val error: PaymentUiError? = null,
        ): State()
    }

    sealed class Effect: UiEffect {
        object Finish: Effect()
    }
}