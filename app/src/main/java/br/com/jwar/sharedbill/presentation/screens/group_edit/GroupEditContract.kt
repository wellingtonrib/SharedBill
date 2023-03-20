package br.com.jwar.sharedbill.presentation.screens.group_edit

import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.util.UiText
import br.com.jwar.sharedbill.presentation.models.GroupMemberUiModel

class GroupEditContract {

    sealed class Event: br.com.jwar.sharedbill.core.common.UiEvent {
        class OnSaveMember(val userName: String, val groupId: String) : Event()
        class OnMemberSelectionChange(val user: GroupMemberUiModel?) : Event()
        class OnMemberDelete(val userId: String, val groupId: String) : Event()
        class OnGroupUpdated(val group: GroupUiModel) : Event()
        object OnSaveGroup: Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val uiModel: GroupUiModel? = null,
        val shouldSelectMemberByName: String? = null,
        val selectedMember: GroupMemberUiModel? = null
    ): br.com.jwar.sharedbill.core.common.UiState

    sealed class Effect: br.com.jwar.sharedbill.core.common.UiEffect {
        object ShowSuccess: Effect()
        object GoToDetails : Effect()
        class ShowError(val message: UiText): Effect()
    }

}