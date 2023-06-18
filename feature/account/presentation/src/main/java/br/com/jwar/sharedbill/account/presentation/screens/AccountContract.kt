package br.com.jwar.sharedbill.account.presentation.screens

import android.content.Intent
import br.com.jwar.sharedbill.account.presentation.model.UserUiModel
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState
import com.google.android.gms.auth.api.identity.BeginSignInResult

class AccountContract {
    sealed class Event: UiEvent {
        object OnInit : Event()
        object OnSignOut : Event()
        object OnRequestSignIn : Event()
        object OnRequestSignUp : Event()
        class OnRequestSignInFirebase(val data: Intent?) : Event()
    }

    sealed class State : UiState {
        object Loading: State()
        class Loaded(val uiModel: UserUiModel = UserUiModel(), val isLoggedIn: Boolean = true): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        class LaunchSignInResult(val signInResult: BeginSignInResult): Effect()
        class ShowError(val message: String): Effect()
    }
}