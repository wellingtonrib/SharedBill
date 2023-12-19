package br.com.jwar.sharedbill.account.presentation.ui.auth

import android.content.Intent
import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.account.domain.usecases.SignInFirebaseUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignInUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignUpUseCase
import br.com.jwar.sharedbill.account.presentation.ui.auth.AuthContract.Effect
import br.com.jwar.sharedbill.account.presentation.ui.auth.AuthContract.Event
import br.com.jwar.sharedbill.account.presentation.ui.auth.AuthContract.State
import br.com.jwar.sharedbill.core.common.BaseViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signInFirebaseUseCase: SignInFirebaseUseCase,
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState() = State.Idle

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnRequestSignIn -> onSignIn()
            is Event.OnRequestSignUp -> onSignUp()
            is Event.OnRequestSignInFirebase -> onSignInFirebase(event.data)
            is Event.OnRequestSignInFirebaseFailed -> onSignInFirebaseFailed()
            is Event.OnPrivacyPolicyClick -> onPrivacyPolicyClick()
        }
    }

    private fun onSignIn() = viewModelScope.launch {
        setLoadingState()
        signInUseCase()
            .onSuccess { sendSignedInEffect(it) }
            .onFailure { emitOnRequestSignUpEvent() }
    }

    private fun onSignUp() = viewModelScope.launch {
        setLoadingState()
        signUpUseCase()
            .onSuccess { sendSignedInEffect(it) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onSignInFirebase(data: Intent?) = viewModelScope.launch {
        setLoadingState()
        signInFirebaseUseCase(data)
            .onSuccess { sendLoggedEffect() }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onPrivacyPolicyClick() = sendEffect { Effect.LaunchPrivacyPolicyIntent }

    private fun onSignInFirebaseFailed() {
        setState { State.Idle }
    }

    private fun sendSignedInEffect(result: BeginSignInResult) =
        sendEffect { Effect.SignedIn(result) }

    private fun emitOnRequestSignUpEvent() = emitEvent { Event.OnRequestSignUp }

    private fun setLoadingState() = setState { State.Loading }

    private fun sendLoggedEffect() {
        setState { State.Idle }
        sendEffect { Effect.LoggedIn }
    }

    private fun sendErrorEffect(throwable: Throwable) {
        setState { State.Idle }
        sendEffect { Effect.ShowError(throwable.message.orEmpty()) }
    }
}