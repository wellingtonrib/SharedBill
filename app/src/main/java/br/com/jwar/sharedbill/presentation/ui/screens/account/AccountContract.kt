package br.com.jwar.sharedbill.presentation.ui.screens.account

import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState

class AccountContract {
    sealed class Event: UiEvent {
        object OnRequestUser : Event()
        object OnRequestSignOut : Event()
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