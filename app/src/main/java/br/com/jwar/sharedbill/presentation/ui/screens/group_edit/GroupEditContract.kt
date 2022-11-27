package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.util.UiText

class GroupEditContract {

    sealed class Event: UiEvent {
        class OnInit(val groupId: String) : Event()
        class OnSaveMemberClick(val userName: String, val groupId: String) : Event()
        class OnMemberSelectionChange(val user: UserUiModel?) : Event()
        class OnMemberDeleteClick(val userId: String, val groupId: String) : Event()
        class OnGroupUpdated(val group: GroupUiModel) : Event()
        object OnSaveGroupClick: Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val group: GroupUiModel? = null,
        val shouldSelectMemberName: String? = null,
        val selectedMember: UserUiModel? = null
    ): UiState

    sealed class Effect: UiEffect {
        class OpenGroupSaved(val group: Group): Effect()
        class ShowError(val error: UiText): Effect()
    }

}