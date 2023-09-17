package br.com.jwar.sharedbill.account.presentation.ui.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract.Effect
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract.Event
import br.com.jwar.sharedbill.core.common.extensions.launchAboutIntent
import br.com.jwar.sharedbill.core.common.extensions.launchPrivacyIntent
import br.com.jwar.sharedbill.core.common.extensions.launchSupportIntent
import br.com.jwar.sharedbill.core.common.extensions.launchTermsIntent

@Composable
fun AccountRoute(
    viewModel: AccountViewModel = hiltViewModel(),
    onNavigateToAuth: () -> Unit,
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    AccountScreen(
        state = state,
        onSignOutClick = { viewModel.emitEvent { Event.OnSignOutClick } },
        onContactClick = { viewModel.emitEvent { Event.OnContactClick } },
        onAboutClick = { viewModel.emitEvent { Event.OnAboutClick } },
        onRateUsClick = { viewModel.emitEvent { Event.OnRateUsClick } },
        onTermsClick = { viewModel.emitEvent { Event.OnTermsClick } },
        onPrivacyClick = { viewModel.emitEvent { Event.OnPrivacyClick } },
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is Effect.NavigateToAuth -> {
                    onNavigateToAuth()
                }
                is Effect.LaunchContactIntent -> {
                    context.launchSupportIntent()
                }
                is Effect.LaunchToTermsIntent -> {
                    context.launchTermsIntent()
                }
                is Effect.LaunchPrivacyIntent -> {
                    context.launchPrivacyIntent()
                }
                is Effect.LaunchAboutIntent -> {
                    context.launchAboutIntent()
                }
                is Effect.LaunchRateUsIntent -> {}
            }
        }
    }
}




