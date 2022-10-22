package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel

class GroupEditContract {

    sealed class Event: UiEvent {
        class OnRequestEdit(val groupId: String) : Event()
        class OnSaveMemberClick(val userName: String, val groupId: String) : Event()
        class OnMemberSelectionChange(val user: UserUiModel?) : Event()
        class OnMemberDeleteClick(val userId: String, val groupId: String) : Event()
        class OnGroupUpdated(val group: GroupUiModel) : Event()
        object OnSaveGroupClick: Event()
    }

    sealed class State: UiState {
        object Loading: State()
        data class Editing(
            val group: GroupUiModel,
            val selectedMember: UserUiModel? = null
        ): State()
    }

    sealed class Effect: UiEffect {
        class OpenGroupSaved(val group: Group): Effect()
        class ShowError(val message: String): Effect()
    }

}