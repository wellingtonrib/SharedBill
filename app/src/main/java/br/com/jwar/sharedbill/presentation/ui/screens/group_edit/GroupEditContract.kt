package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState

class GroupEditContract {

    sealed class Event: UiEvent {
        class OnRequestEdit(val groupId: String) : Event()
        class OnSaveGroupClick(val group: Group) : Event()
        class OnSaveMemberClick(val userName: String, val groupId: String) : Event()
        class OnMemberSelectionChange(val user: User?) : Event()
        class OnMemberDeleteClick(val userId: String, val groupId: String) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        data class Editing(
            val group: Group,
            val selectedMember: User? = null
        ): State()
    }

    sealed class Effect: UiEffect {
        class OpenGroupSaved(val group: Group): Effect()
        class ShowError(val message: String): Effect()
    }

}