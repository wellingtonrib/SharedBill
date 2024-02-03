package br.com.jwar.sharedbill.groups.presentation.ui.group_details

import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState

class GroupDetailsContract {

    sealed class Event: UiEvent {
        class OnInit(val groupId: String) : Event()
        object OnEditGroup : Event()
        class OnNewPayment(val paymentType: PaymentType) : Event()
        class OnShareBalance(val balance: String) : Event()
        class OnDeletePayment(val paymentId: String, val groupId: String) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        data class Loaded(val uiModel: GroupUiModel): State()
        data class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object NavigateToGroupEdit: Effect()
        class NavigateToNewPayment(val paymentType: PaymentType): Effect()
        class ShareBalance(val balance: String): Effect()
    }

}