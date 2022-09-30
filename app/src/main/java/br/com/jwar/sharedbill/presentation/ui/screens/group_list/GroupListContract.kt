package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.core.UiEffect
import br.com.jwar.sharedbill.presentation.core.UiEvent
import br.com.jwar.sharedbill.presentation.core.UiState

class GroupListContract {
    sealed class Event: UiEvent {
        class OnGetGroups(val refresh: Boolean) : Event()
        object OnNewGroup: Event()
        class OnGroupSelected(val group: Group): Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Loaded(val groups: List<Group>): State()
        class Error(val message: String?): State()
    }

    sealed class Effect: UiEffect {
        class OpenGroupDetails(val groupId: String): Effect()
        object OpenGroupCreate: Effect()
    }
}