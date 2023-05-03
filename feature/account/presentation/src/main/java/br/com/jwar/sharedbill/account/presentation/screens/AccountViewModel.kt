package br.com.jwar.sharedbill.account.presentation.screens

import android.content.Intent
import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.account.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.usecases.*
import br.com.jwar.sharedbill.account.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.account.presentation.screens.AccountContract.Effect
import br.com.jwar.sharedbill.account.presentation.screens.AccountContract.Event
import br.com.jwar.sharedbill.account.presentation.screens.AccountContract.State
import com.google.android.gms.auth.api.identity.BeginSignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signInFirebaseUseCase: SignInFirebaseUseCase,
    private val userToUserUiModelMapper: UserToUserUiModelMapper,
): BaseViewModel<Event, State, Effect>() {

    init { onInit() }

    override fun getInitialState() = State.Loading

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnInit -> onInit()
            is Event.OnSignOut -> onSignOut()
            is Event.OnRequestSignIn -> onSignIn()
            is Event.OnRequestSignUp -> onSignUp()
            is Event.OnRequestSignInFirebase -> onSignInFirebase(event.data)
        }
    }

    private fun onInit() = viewModelScope.launch {
        setLoadingState()
        getCurrentUserUseCase()
            .onSuccess { setLoadedState(it) }
            .onFailure { handleException(it) }
    }

    private fun onSignOut() = viewModelScope.launch {
        setLoadingState()
        signOutUseCase()
            .onSuccess { setLoggedOutState() }
            .onFailure { handleException(it) }
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
            .onFailure { setErrorState(it) }
    }

    private fun onSignInFirebase(data: Intent?) = viewModelScope.launch {
        setLoadingState()
        signInFirebaseUseCase(data)
            .onSuccess { onInit() }
            .onFailure { setErrorState(it) }
    }

    private fun sendSignedInEffect(result: BeginSignInResult) =
        sendEffect { Effect.LaunchSignInResult(result) }

    private fun emitOnRequestSignUpEvent() = emitEvent { Event.OnRequestSignUp }

    private fun setLoadingState() = setState { State.Loading }

    private fun setLoadedState(user: User) =
        setState { State.Loaded(uiModel = userToUserUiModelMapper.mapFrom(user)) }

    private fun setLoggedOutState() = setState { State.Loaded(isLoggedIn = false) }

    private fun handleException(throwable: Throwable) {
        if (throwable is UserNotFoundException) {
            setLoggedOutState()
        } else {
            setErrorState(throwable)
        }
    }

    private fun setErrorState(throwable: Throwable) =
        setState { State.Error(throwable.message.orEmpty()) }
}