package br.com.jwar.sharedbill.presentation.ui.screens.auth

import android.content.Intent
import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Result
import br.com.jwar.sharedbill.domain.usecases.SignInFirebaseUseCase
import br.com.jwar.sharedbill.domain.usecases.SignInUseCase
import br.com.jwar.sharedbill.domain.usecases.SignUpUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract.*
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

    override fun getInitialState() = State(isAuthenticating = false)

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnRequestSignIn -> onSignIn()
            is Event.OnRequestSignUp -> onSignUp()
            is Event.OnRequestSignInFirebase -> onSignInFirebase(event.data)
        }
    }

    private fun onSignIn() = viewModelScope.launch {
        setAuthenticatingState()
        when (val result = signInUseCase()) {
            is Result.Success -> sendSignedInEffect(result.data)
            is Result.Error -> emitOnRequestSignUpEvent()
        }
    }

    private fun onSignUp() = viewModelScope.launch {
        setAuthenticatingState()
        when (val result = signUpUseCase()) {
            is Result.Success -> sendSignedInEffect(result.data)
            is Result.Error -> setErrorState(result.exception)
        }
    }

    private fun onSignInFirebase(data: Intent?) = viewModelScope.launch {
        setAuthenticatingState()
        when (val result = signInFirebaseUseCase(data)) {
            is Result.Success -> sendGoHomeEffect()
            is Result.Error -> setErrorState(result.exception)
        }
    }

    private fun setAuthenticatingState() = setState { it.copy(isAuthenticating = true) }

    private fun sendSignedInEffect(result: BeginSignInResult) =
        sendEffect { Effect.LaunchSignInResult(result) }

    private fun setErrorState(throwable: Throwable) {
        setState { it.copy(isAuthenticating = false) }
        sendEffect { Effect.ShowError(throwable.localizedMessage.orEmpty()) }
    }

    private fun sendGoHomeEffect() = sendEffect { Effect.GoToHome }

    private fun emitOnRequestSignUpEvent() = emitEvent { Event.OnRequestSignUp }
}