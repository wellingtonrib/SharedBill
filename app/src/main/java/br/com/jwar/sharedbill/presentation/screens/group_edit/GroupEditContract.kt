package br.com.jwar.sharedbill.presentation.screens.group_edit

import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.util.UiText

class GroupEditContract {

    sealed class Event: UiEvent {
        class OnSaveMember(val userName: String, val groupId: String) : Event()
        class OnMemberSelectionChange(val user: UserUiModel?) : Event()
        class OnMemberDelete(val userId: String, val groupId: String) : Event()
        class OnGroupUpdated(val group: GroupUiModel) : Event()
        object OnSaveGroup: Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val uiModel: GroupUiModel? = null,
        val shouldSelectMemberByName: String? = null,
        val selectedMember: UserUiModel? = null
    ): UiState

    sealed class Effect: UiEffect {
        object ShowSuccess: Effect()
        object GoToDetails : Effect()
        class ShowError(val message: UiText): Effect()
    }

}