package br.com.jwar.sharedbill.presentation.base.screen_model

import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState

class ModelContract {

    sealed class Event: br.com.jwar.sharedbill.core.common.UiEvent {
        object OnLoad: Event()
    }

    sealed class State: br.com.jwar.sharedbill.core.common.UiState {
        object Loading: State()
        object Loaded: State()
        object Error: State()
    }

    sealed class Effect: br.com.jwar.sharedbill.core.common.UiEffect {
        object OpenNext: Effect()
    }

}