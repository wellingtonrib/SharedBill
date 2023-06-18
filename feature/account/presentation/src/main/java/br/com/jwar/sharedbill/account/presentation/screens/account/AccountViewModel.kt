package br.com.jwar.sharedbill.account.presentation.screens.account

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.account.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.usecases.GetCurrentUserUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignOutUseCase
import br.com.jwar.sharedbill.account.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.account.presentation.screens.account.AccountContract.Effect
import br.com.jwar.sharedbill.account.presentation.screens.account.AccountContract.Event
import br.com.jwar.sharedbill.account.presentation.screens.account.AccountContract.State
import br.com.jwar.sharedbill.core.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userToUserUiModelMapper: UserToUserUiModelMapper,
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
            .onSuccess { sendLoggedOutEffect() }
            .onFailure { handleException(it) }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setLoadedState(user: User) =
        setState { State.Loaded(uiModel = userToUserUiModelMapper.mapFrom(user)) }

    private fun sendLoggedOutEffect() = sendEffect { Effect.NavigateToAuth }

    private fun handleException(throwable: Throwable) {
        if (throwable is UserNotFoundException) {
            sendLoggedOutEffect()
        } else {
            setErrorState(throwable)
        }
    }

    private fun setErrorState(throwable: Throwable) =
        setState { State.Error(throwable.message.orEmpty()) }
}