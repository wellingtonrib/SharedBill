package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState

class GroupListContract {

    sealed class Event: UiEvent {
        class OnRequestGroups(val refresh: Boolean) : Event()
        class OnGroupCreate(val title: String): Event()
        class OnGroupJoin(val code: String): Event()
        class OnGroupSelect(val group: Group): Event()
    }

    sealed class State: UiState {
        object Loading: State()
        object Empty: State()
        class Loaded(val groups: List<Group>): State()
        class Error(val message: String?): State()
    }

    sealed class Effect: UiEffect {
        class OpenGroupDetails(val groupId: String): Effect()
        object GoToAuth : Effect()
    }

}