package br.com.jwar.sharedbill.account.presentation.ui.auth

import android.content.Intent
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState
import com.google.android.gms.auth.api.identity.BeginSignInResult

class AuthContract {
    sealed class Event: UiEvent {
        object OnRequestSignIn : Event()
        object OnRequestSignUp : Event()
        class OnRequestSignInFirebase(val data: Intent?) : Event()
        object OnRequestSignInFirebaseFailed : Event()
    }

    sealed class State : UiState {
        object Loading: State()
        object Idle: State()
    }

    sealed class Effect: UiEffect {
        object LoggedIn : Effect()
        class LaunchSignInResult(val signInResult: BeginSignInResult): Effect()
        class ShowError(val message: String): Effect()
    }
}