package br.com.jwar.sharedbill.presentation.ui.screens.account

import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.core.UiEffect
import br.com.jwar.sharedbill.presentation.core.UiEvent
import br.com.jwar.sharedbill.presentation.core.UiState

class AccountContract {
    sealed class Event: UiEvent {
        object GetUser : Event()
        object SignOut : Event()
    }

    sealed class State : UiState  {
        object Loading: State()
        class Loaded(val user: User): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object GoToAuth : Effect()
    }
}