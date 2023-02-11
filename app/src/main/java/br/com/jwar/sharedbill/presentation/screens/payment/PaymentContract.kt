package br.com.jwar.sharedbill.presentation.screens.payment

import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import java.util.*

class PaymentContract {

    sealed class Event: UiEvent {
        class OnParamsChange(val params: PaymentParams) : Event()
        object OnCreatePayment : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val params: PaymentParams? = null,
    ): UiState

    sealed class Effect: UiEffect {
        object Finish: Effect()
    }

    data class PaymentParams(
        val description: String = "",
        val value: String = "",
        val paidBy: UserUiModel = UserUiModel(),
        val paidTo: List<UserUiModel> = emptyList(),
        val date: Date = Date(),
        val group: GroupUiModel = GroupUiModel(),
        val error: PaymentUiError? = null
    ) {
        companion object {
            fun sample() = PaymentParams(
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