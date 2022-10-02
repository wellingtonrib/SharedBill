package br.com.jwar.sharedbill.presentation.base.screen_model

import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState

class ModelContract {

    sealed class Event: UiEvent {
        object OnLoad: Event()
    }

    sealed class State: UiState {
        object Loading: State()
        object Loaded: State()
        object Error: State()
    }

    sealed class Effect: UiEffect {
        object OpenNext: Effect()
    }

}