package br.com.jwar.sharedbill.presentation.ui.screens.auth

import android.content.Intent
import br.com.jwar.sharedbill.presentation.core.UiEffect
import br.com.jwar.sharedbill.presentation.core.UiEvent
import br.com.jwar.sharedbill.presentation.core.UiState
import com.google.android.gms.auth.api.identity.BeginSignInResult

class AuthContract {
    sealed class Event: UiEvent {
        object OnSignIn : Event()
        object OnSignUp : Event()
        class OnSignInFirebase(val data: Intent?) : Event()
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