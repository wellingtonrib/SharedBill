package br.com.jwar.sharedbill.presentation.screens.group_details

import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel

class GroupDetailsContract {

    sealed class Event: UiEvent {
        object OnRefresh : Event()
        object OnManage : Event()
        class OnNewPayment(val groupId: String) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Loaded(val uiModel: GroupUiModel): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object OpenGroupEdit: Effect()
        class OpenNewPayment(val groupId: String): Effect()
    }

}