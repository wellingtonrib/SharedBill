package br.com.jwar.sharedbill.presentation.ui.screens.group_create

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.core.UiEffect
import br.com.jwar.sharedbill.presentation.core.UiEvent
import br.com.jwar.sharedbill.presentation.core.UiState

class GroupCreateContract {
    sealed class Event: UiEvent {
        class OnCreateGroup(val name: String) : Event()
    }

    sealed class State: UiState {
        object Idle: State()
        object Creating: State()
    }

    sealed class Effect: UiEffect {
        class OpenGroupCreated(val group: Group): Effect()
        class ShowError(val message: String): Effect()
    }
}