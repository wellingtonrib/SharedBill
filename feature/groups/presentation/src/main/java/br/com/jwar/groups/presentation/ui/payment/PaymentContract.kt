package br.com.jwar.groups.presentation.ui.payment

import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState
import br.com.jwar.sharedbill.groups.domain.model.PaymentType

class PaymentContract {

    sealed class Event: UiEvent {
        data class OnSavePayment(val payment: PaymentUiModel) : Event()
    }

    sealed class State(
    ): UiState {
        object Loading: State()
        data class Loaded(
            val group: GroupUiModel,
            val paymentType: PaymentType = PaymentType.EXPENSE,
            val error: PaymentUiError? = null,
        ): State() {
            val paidByOptions: Map<GroupMemberUiModel, Boolean>
                get() = group.members.associateWith { it.uid == group.members.first().uid }
            val paidToOptions: List<GroupMemberUiModel>
                get() = when (paymentType) {
                    PaymentType.EXPENSE -> group.members
                    PaymentType.SETTLEMENT -> group.members.filterNot { it.isCurrentUser }
                }
            val paidByDefault: GroupMemberUiModel
                get() = paidByOptions.keys.first()
            val paidToDefault: GroupMemberUiModel
                get() = paidToOptions.first()
        }
    }

    sealed class Effect: UiEffect {
        object Finish: Effect()
    }
}