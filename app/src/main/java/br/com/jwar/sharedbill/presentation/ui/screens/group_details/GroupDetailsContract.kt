package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel

class GroupDetailsContract {

    sealed class Event: UiEvent {
        class OnInit(val groupId: String, val refresh: Boolean) : Event()
        object OnManageClick : Event()
        class OnNewPaymentClick(val groupId: String) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Loaded(val group: GroupUiModel): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object OpenGroupMembers: Effect()
        class OpenNewPayment(val groupId: String): Effect()
    }

}