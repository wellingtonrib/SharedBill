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

private fun Context.launchSupportIntent() = this.sendEmail(
    addresses = arrayOf(this.getString(R.string.app_contact_mail)),
    subject = this.getString(R.string.app_contact_subject)
)

private fun Context.launchTermsIntent() =
    this.openUrl(this.getString(R.string.app_terms_link))

private fun Context.launchPrivacyIntent() =
    this.openUrl(this.getString(R.string.app_privacy_link))

private fun Context.launchAboutIntent() =
    this.openUrl(this.getString(R.string.app_about_link))


