package br.com.jwar.sharedbill

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState

class MainContract {
    sealed class Event: UiEvent {
        object OnInit: Event()
    }
    sealed class State: UiState {
        object Initializing : State()
        data class Initialized(val currentUser: User?) : State()
    }

    sealed class Effect: UiEffect {
        object NavigateToAuth: Effect()
    }
}