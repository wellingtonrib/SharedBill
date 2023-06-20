package br.com.jwar.groups.presentation.screens.group_edit

import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiState
import br.com.jwar.sharedbill.core.designsystem.util.UiText

class GroupEditContract {

    sealed class Event: br.com.jwar.sharedbill.core.common.UiEvent {
        class OnSaveMember(val userName: String, val groupId: String) : Event()
        class OnMemberSelect(val user: GroupMemberUiModel?) : Event()
        class OnMemberDelete(val userId: String, val groupId: String) : Event()
        class OnShareInviteCode(val inviteCode: String) : Event()
        class OnGroupUpdated(val group: GroupUiModel) : Event()
        object OnSaveGroup: Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val uiModel: GroupUiModel? = null,
        val selectedMember: GroupMemberUiModel? = null
    ): UiState

    sealed class Effect: UiEffect {
        object ShowSuccess: Effect()
        object NavigateToGroupDetails : Effect()
        class ShowError(val message: UiText): Effect()
        class ShareInviteCode(val inviteCode: String): Effect()
    }

}