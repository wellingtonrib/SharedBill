package br.com.jwar.sharedbill.presentation.screens.auth

import android.content.Intent
import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import com.google.android.gms.auth.api.identity.BeginSignInResult

class AuthContract {

    sealed class Event: UiEvent {
        object OnRequestSignIn : Event()
        object OnRequestSignUp : Event()
        class OnRequestSignInFirebase(val data: Intent?) : Event()
    }

    data class State(
        val isAuthenticating: Boolean = false
    ) : UiState

    sealed class Effect: UiEffect {
        object GoToHome: Effect()
        class LaunchSignInResult(val signInResult: BeginSignInResult): Effect()
        class ShowError(val message: String): Effect()
    }

}