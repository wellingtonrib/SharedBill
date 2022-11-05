package br.com.jwar.sharedbill.presentation.ui.screens.account

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Resource.Failure
import br.com.jwar.sharedbill.domain.model.Resource.Loading
import br.com.jwar.sharedbill.domain.model.Resource.Success
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
            is Event.OnRequestUser -> onRequestUser()
            is Event.OnRequestSignOut -> onRequestSignOut()
        }
    }

    private fun onRequestUser() = viewModelScope.launch {
        getUserUseCase().collect { resource ->
            when(resource) {
                is Loading -> setLoadingState()
                is Success -> setLoadedState(resource)
                is Failure -> handleException(resource.throwable)
            }
        }
    }

    private fun setLoadedState(resource: Success<User>) {
        setState { State.Loaded(userToUserUiModelMapper.mapFrom(resource.data)) }
    }

    private fun onRequestSignOut() = viewModelScope.launch {
        signOutUseCase().collect { resource ->
            when(resource) {
                is Loading -> setLoadingState()
                is Success -> sendGoAuthEffect()
                is Failure -> handleException(resource.throwable)
            }
        }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun sendGoAuthEffect() =
        sendEffect { Effect.GoToAuth }

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