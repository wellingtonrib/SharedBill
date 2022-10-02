package br.com.jwar.sharedbill.presentation.ui.screens.group_create

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState

class GroupEditContract {

    sealed class Event: UiEvent {
        class OnSaveClick(val name: String) : Event()
    }

    sealed class State: UiState {
        object Idle: State()
        object Saving: State()
    }

    sealed class Effect: UiEffect {
        class OpenGroupSaved(val group: Group): Effect()
        class ShowError(val message: String): Effect()
    }

}