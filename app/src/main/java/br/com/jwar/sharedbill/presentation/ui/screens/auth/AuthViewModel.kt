package br.com.jwar.sharedbill.presentation.ui.screens.auth

import android.content.Intent
import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Resource.Failure
import br.com.jwar.sharedbill.domain.model.Resource.Loading
import br.com.jwar.sharedbill.domain.model.Resource.Success
import br.com.jwar.sharedbill.domain.usecases.SignInFirebaseUseCase
import br.com.jwar.sharedbill.domain.usecases.SignInUseCase
import br.com.jwar.sharedbill.domain.usecases.SignUpUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract.State
import com.google.android.gms.auth.api.identity.BeginSignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

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
        signInUseCase().collect { resource ->
            when(resource) {
                is Loading -> setAuthenticatingState()
                is Success -> sendSignedInEffect(resource)
                is Failure -> emitOnRequestSignUpEvent()
            }
        }
    }

    private fun onSignUp() = viewModelScope.launch {
        signUpUseCase().collect { resource ->
            when(resource) {
                is Loading -> setAuthenticatingState()
                is Success -> sendSignedInEffect(resource)
                is Failure -> setErrorState(resource.throwable)
            }
        }
    }

    private fun onSignInFirebase(data: Intent?) = viewModelScope.launch {
        signInFirebaseUseCase(data).collect { resource ->
            when(resource) {
                is Loading -> setAuthenticatingState()
                is Success -> sendGoHomeEffect()
                is Failure -> setErrorState(resource.throwable)
            }
        }
    }

    private fun setAuthenticatingState() = setState { State(isAuthenticating = true) }

    private fun sendSignedInEffect(resource: Success<BeginSignInResult>) =
        sendEffect { Effect.LaunchSignInResult(resource.data) }

    private fun setErrorState(throwable: Throwable) {
        setState { State(isAuthenticating = false) }
        sendEffect { Effect.ShowError(throwable.localizedMessage.orEmpty()) }
    }

    private fun sendGoHomeEffect() =
        sendEffect { Effect.GoToHome }

    private fun emitOnRequestSignUpEvent() =
        emitEvent { Event.OnRequestSignUp }
}