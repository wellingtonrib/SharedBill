package br.com.jwar.sharedbill.account.presentation.ui.account

import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel

class AccountContract {
    sealed class Event: UiEvent {
        object OnInit : Event()
        object OnSignOutClick : Event()
        object OnContactClick : Event()
        object OnAboutClick : Event()
        object OnTermsClick : Event()
        object OnPrivacyClick : Event()
        object OnRateUsClick : Event()
    }

    data class State(
        val uiModel: UserUiModel = UserUiModel(),
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
        val showAboutDialog: Boolean = false,
    ) : UiState

    sealed class Effect: UiEffect {
        object NavigateToAuth: Effect()
        object LaunchContactIntent : Effect()
        object LaunchToTermsIntent : Effect()
        object LaunchPrivacyIntent : Effect()
        object LaunchRateUsIntent : Effect()
        object LaunchAboutIntent : Effect()
    }
}