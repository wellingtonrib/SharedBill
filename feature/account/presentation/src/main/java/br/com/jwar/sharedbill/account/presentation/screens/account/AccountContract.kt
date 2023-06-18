package br.com.jwar.sharedbill.account.presentation.screens.account

import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel

class AccountContract {
    sealed class Event: UiEvent {
        object OnInit : Event()
        object OnSignOut : Event()
    }

    sealed class State : UiState {
        object Loading: State()
        class Loaded(val uiModel: UserUiModel = UserUiModel()): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object NavigateToAuth: Effect()
    }
}