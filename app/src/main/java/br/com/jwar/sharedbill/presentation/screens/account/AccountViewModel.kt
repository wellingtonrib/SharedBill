package br.com.jwar.sharedbill.presentation.screens.account

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.usecases.GetCurrentUserUseCase
import br.com.jwar.sharedbill.domain.usecases.SignOutUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.presentation.screens.account.AccountContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userToUserUiModelMapper: UserToUserUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    init { onInit() }

    override fun getInitialState() = State.Loading

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnInit -> onInit()
            is Event.OnSignOut -> onSignOut()
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
            .onSuccess { sendGoAuthEffect() }
            .onFailure { handleException(it) }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setLoadedState(user: User) =
        setState { State.Loaded(userToUserUiModelMapper.mapFrom(user)) }

    private fun sendGoAuthEffect() = sendEffect { Effect.GoToAuth }

    private fun handleException(throwable: Throwable) {
        if (throwable is UserNotFoundException) {
            sendGoAuthEffect()
        } else {
            setErrorState(throwable)
        }
    }

    private fun setErrorState(throwable: Throwable) =
        setState { State.Error(throwable.message.orEmpty()) }
}