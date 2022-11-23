package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel

class GroupListContract {

    sealed class Event: UiEvent {
        object OnInit : Event()
        class OnGroupCreate(val title: String): Event()
        class OnGroupJoin(val inviteCode: String): Event()
        class OnGroupSelect(val groupId: String): Event()
    }

    sealed class State: UiState {
        object Loading: State()
        object Empty: State()
        class Loaded(val groups: List<GroupUiModel>): State()
        class Error(val message: String?): State()
    }

    sealed class Effect: UiEffect {
        class OpenGroupDetails(val groupId: String): Effect()
        object GoToAuth : Effect()
    }

}