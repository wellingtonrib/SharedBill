package br.com.jwar.sharedbill.presentation.ui.screens.account

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.exceptions.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Resource.Failure
import br.com.jwar.sharedbill.domain.model.Resource.Loading
import br.com.jwar.sharedbill.domain.model.Resource.Success
import br.com.jwar.sharedbill.domain.usecases.GetUserUseCase
import br.com.jwar.sharedbill.domain.usecases.SignOutUseCase
import br.com.jwar.sharedbill.presentation.core.BaseViewModel
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
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState() = State.Loading

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnGetUser -> onGetUser()
            is Event.OnSignOut -> onSignOut()
        }
    }

    private fun onGetUser() = viewModelScope.launch {
        getUserUseCase().collect { resource ->
            when(resource) {
                is Loading -> setState { State.Loading }
                is Success -> setState { State.Loaded(resource.data) }
                is Failure -> handleException(resource.throwable)
            }
        }
    }

    private fun onSignOut() = viewModelScope.launch {
        signOutUseCase().collect { resource ->
            when(resource) {
                is Loading -> setState { State.Loading }
                is Success -> sendEffect { Effect.GoToAuth }
                is Failure -> handleException(resource.throwable)
            }
        }
    }

    private fun handleException(throwable: Throwable) {
        if (throwable is UserNotFoundException) {
            sendEffect { Effect.GoToAuth }
        } else {
            setState { State.Error(throwable.message.orEmpty()) }
        }
    }
}