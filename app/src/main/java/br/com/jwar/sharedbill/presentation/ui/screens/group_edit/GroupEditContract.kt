package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState

class GroupEditContract {

    sealed class Event: UiEvent {
        class OnRequestEdit(val groupId: String) : Event()
        class OnSaveGroupClick(val group: Group) : Event()
        class OnSaveMemberClick(val userName: String, val group: Group) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Editing(val group: Group): State()
    }

    sealed class Effect: UiEffect {
        class OpenGroupSaved(val group: Group): Effect()
        class ShowError(val message: String): Effect()
    }

}