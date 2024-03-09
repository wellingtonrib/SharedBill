package br.com.jwar.sharedbill.account.presentation.ui.account

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.account.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.usecases.GetCurrentUserUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignOutUseCase
import br.com.jwar.sharedbill.account.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract.Effect
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract.Event
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract.State
import br.com.jwar.sharedbill.core.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userToUserUiModelMapper: UserToUserUiModelMapper,
) : BaseViewModel<Event, State, Effect>() {

    override fun getInitialState() = State(isLoading = true)

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnInit -> onInit()
            is Event.OnSignOutClick -> onSignOut()
            is Event.OnContactClick -> onContact()
            is Event.OnAboutClick -> onAbout()
            is Event.OnTermsClick -> onTerms()
            is Event.OnPrivacyClick -> onPrivacy()
            is Event.OnRateUsClick -> onRateUs()
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

    private fun onContact() {
        sendEffect { Effect.LaunchContactIntent }
    }

    private fun onTerms() {
        sendEffect { Effect.LaunchToTermsIntent }
    }

    private fun onAbout() {
        sendEffect { Effect.LaunchAboutIntent }
    }

    private fun onPrivacy() {
        sendEffect { Effect.LaunchPrivacyIntent }
    }

    private fun onRateUs() {
        sendEffect { Effect.LaunchRateUsIntent }
    }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun setLoadedState(user: User) =
        setState { it.copy(isLoading = false, uiModel = userToUserUiModelMapper.mapFrom(user)) }

    private fun sendLoggedOutEffect() = sendEffect { Effect.NavigateToAuth }

    private fun handleException(throwable: Throwable) {
        if (throwable is UserNotFoundException) {
            sendLoggedOutEffect()
        }
    }
}
