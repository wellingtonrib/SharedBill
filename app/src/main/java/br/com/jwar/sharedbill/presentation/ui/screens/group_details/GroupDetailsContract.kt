package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState

class GroupDetailsContract {

    sealed class Event: UiEvent {
        class OnRequestGroup(val groupId: String) : Event()
        object OnManageClick : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Loaded(val group: Group): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object OpenGroupMembers: Effect()
    }

}