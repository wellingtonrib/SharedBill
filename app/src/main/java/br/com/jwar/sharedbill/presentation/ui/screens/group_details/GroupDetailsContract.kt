package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.core.UiEffect
import br.com.jwar.sharedbill.presentation.core.UiEvent
import br.com.jwar.sharedbill.presentation.core.UiState

class GroupDetailsContract {
    sealed class Event: UiEvent {
        class OnLoadGroup(val groupId: String) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Loaded(val group: Group): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {

    }
}