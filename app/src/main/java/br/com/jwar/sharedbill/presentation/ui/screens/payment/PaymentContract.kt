package br.com.jwar.sharedbill.presentation.ui.screens.payment

import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.util.UiText
import java.util.Date

class PaymentContract {

    sealed class Event: UiEvent {
        class OnInit(val groupId: String): Event()
        class OnPaymentParamsChange(val params: SendPaymentParams) : Event()
        object OnCreatePayment : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val params: SendPaymentParams? = null,
    ): UiState

    sealed class Effect: UiEffect {
        object Finish: Effect()
        class ShowError(val text: UiText): Effect()
    }

    data class SendPaymentParams(
        val description: String = "",
        val value: String = "",
        val paidBy: UserUiModel = UserUiModel(),
        val paidTo: List<UserUiModel> = emptyList(),
        val date: Date = Date(),
        val group: GroupUiModel = GroupUiModel(),
        val error: PaymentUiError? = null
    ) {
        companion object {
            fun sample() = SendPaymentParams(
                group = GroupUiModel.sample(),
                paidBy = UserUiModel.sample(),
                paidTo = listOf(
                    UserUiModel.sample(),
                    UserUiModel.sample(),
                    UserUiModel.sample(),
                )
            )
        }
    }
}