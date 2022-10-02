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
                is Loading -> setState { it.copy(isAuthenticating = true) }
                is Success -> sendEffect { Effect.LaunchSignInResult(resource.data) }
                is Failure -> emitEvent { Event.OnRequestSignUp }
            }
        }
    }

    private fun onSignUp() = viewModelScope.launch {
        signUpUseCase().collect { resource ->
            when(resource) {
                is Loading -> setState { it.copy(isAuthenticating = true) }
                is Success -> sendEffect { Effect.LaunchSignInResult(resource.data) }
                is Failure -> handleException(resource.throwable)
            }
        }
    }

    private fun onSignInFirebase(data: Intent?) = viewModelScope.launch {
        signInFirebaseUseCase(data).collect { resource ->
            when(resource) {
                is Loading -> setState { it.copy(isAuthenticating = true) }
                is Success -> sendEffect { Effect.GoToHome }
                is Failure -> handleException(resource.throwable)
            }
        }
    }

    private fun handleException(throwable: Throwable) {
        setState { it.copy(isAuthenticating = false) }
        sendEffect { Effect.ShowError(throwable.localizedMessage.orEmpty()) }
    }
}