package br.com.jwar.sharedbill.presentation.ui.screens.group_members

import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState

class GroupMembersContract {

    sealed class Event: UiEvent {
        class OnRequestMembers(val groupId: String, val refresh: Boolean = false): Event()
        object OnAddMemberClick: Event()
        object OnMemberSelect: Event()
        object OnSaveMemberClick: Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Loaded(val members: List<User>): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object OpenMemberCreate: Effect()
        object OpenMemberDetails: Effect()
    }

}