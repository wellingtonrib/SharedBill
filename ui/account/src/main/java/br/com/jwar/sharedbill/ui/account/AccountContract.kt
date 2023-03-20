package br.com.jwar.sharedbill.ui.account

import android.content.Intent
import br.com.jwar.sharedbill.ui.account.model.UserUiModel
import com.google.android.gms.auth.api.identity.BeginSignInResult

class AccountContract {
    sealed class Event: br.com.jwar.sharedbill.core.common.UiEvent {
        object OnInit : Event()
        object OnSignOut : Event()
        object OnRequestSignIn : Event()
        object OnRequestSignUp : Event()
        class OnRequestSignInFirebase(val data: Intent?) : Event()
    }

    sealed class State : br.com.jwar.sharedbill.core.common.UiState {
        object Loading: State()
        class Loaded(val uiModel: UserUiModel = UserUiModel(), val isLoggedIn: Boolean = true): State()
        class Error(val message: String): State()
    }

    sealed class Effect: br.com.jwar.sharedbill.core.common.UiEffect {
        class LaunchSignInResult(val signInResult: BeginSignInResult): Effect()
        class ShowError(val message: String): Effect()
    }
}