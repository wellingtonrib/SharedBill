package br.com.jwar.sharedbill.presentation.screens.payment

import br.com.jwar.sharedbill.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import java.util.*

class PaymentContract {

    sealed class Event: br.com.jwar.sharedbill.core.common.UiEvent {
        class OnParamsChange(val params: PaymentParams) : Event()
        object OnCreatePayment : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val params: PaymentParams? = null,
    ): br.com.jwar.sharedbill.core.common.UiState

    sealed class Effect: br.com.jwar.sharedbill.core.common.UiEffect {
        object Finish: Effect()
    }

    data class PaymentParams(
        val description: String = "",
        val value: String = "",
        val paidBy: GroupMemberUiModel = GroupMemberUiModel(),
        val paidTo: List<GroupMemberUiModel> = emptyList(),
        val date: Date = Date(),
        val group: GroupUiModel = GroupUiModel(),
        val error: PaymentUiError? = null
    ) {
        companion object {
            fun sample() = PaymentParams(
                group = GroupUiModel.sample(),
                paidBy = GroupMemberUiModel.sample(),
                paidTo = listOf(
                    GroupMemberUiModel.sample(),
                    GroupMemberUiModel.sample(),
                    GroupMemberUiModel.sample(),
                )
            )
        }
    }
}