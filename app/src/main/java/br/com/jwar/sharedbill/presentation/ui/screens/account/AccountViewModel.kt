package br.com.jwar.sharedbill.presentation.ui.screens.account

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.usecases.GetUserUseCase
import br.com.jwar.sharedbill.domain.usecases.SignOutUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val userToUserUiModelMapper: UserToUserUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState() = State.Loading

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnInit -> onInit()
            is Event.OnSignOut -> onSignOut()
        }
    }

    private fun onInit() = viewModelScope.launch {
        setLoadingState()
        getUserUseCase()
            .onSuccess { setLoadedState(it) }
            .onFailure { handleException(it) }
    }

    private fun onSignOut() = viewModelScope.launch {
        signOutUseCase()
        sendGoAuthEffect()
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