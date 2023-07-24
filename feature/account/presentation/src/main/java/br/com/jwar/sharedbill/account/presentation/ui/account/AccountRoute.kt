package br.com.jwar.sharedbill.account.presentation.ui.account

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.account.presentation.R
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract.Effect
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract.Event
import br.com.jwar.sharedbill.core.utility.extensions.openUrl
import br.com.jwar.sharedbill.core.utility.extensions.sendEmail

@Composable
fun AccountRoute(
    viewModel: AccountViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNavigateToAuth: () -> Unit,
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    AccountScreen(
        state = state,
        onSignOutClick = { viewModel.emitEvent { Event.OnSignOutClick } },
        onSupportClick = { viewModel.emitEvent { Event.OnSupportClick } },
        onAboutClick = { viewModel.emitEvent { Event.OnAboutClick } },
        onAboutDismiss = { viewModel.emitEvent { Event.OnAboutDismiss } },
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
                is Effect.LaunchSupportIntent -> {
                    context.launchSupportIntent()
                }
                is Effect.NavigateToTerms -> {
                    context.launchTermsIntent()
                }
                is Effect.NavigateToPrivacy -> {
                    context.launchPrivacyIntent()
                }
                is Effect.LaunchRateUsIntent -> {}
            }
        }
    }
}

private fun Context.launchSupportIntent() = this.sendEmail(
    addresses = arrayOf(this.getString(R.string.app_support_mail)),
    subject = this.getString(R.string.app_support_subject)
)

private fun Context.launchTermsIntent() =
    this.openUrl(this.getString(R.string.app_terms_link))

private fun Context.launchPrivacyIntent() =
    this.openUrl(this.getString(R.string.app_privacy_link))


