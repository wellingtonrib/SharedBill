package br.com.jwar.sharedbill.account.presentation.ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.account.presentation.ui.auth.AuthContract.Effect
import br.com.jwar.sharedbill.core.common.extensions.launchPrivacyIntent
import com.google.android.gms.auth.api.identity.BeginSignInResult

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthRoute(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        AuthScreen(
            state = state,
            onSignInClick = { viewModel.emitEvent { AuthContract.Event.OnRequestSignIn } },
            onPrivacyClick = { viewModel.emitEvent { AuthContract.Event.OnPrivacyPolicyClick } },
        )
    }

    val launcherForActivityResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.emitEvent { AuthContract.Event.OnRequestSignInFirebase(result.data) }
        } else {
            viewModel.emitEvent { AuthContract.Event.OnRequestSignInFirebaseFailed }
        }
    }

    fun launchSignInResult(signInResult: BeginSignInResult) {
        launcherForActivityResult.launch(
            IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        )
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is Effect.SignedIn -> {
                    launchSignInResult(effect.signInResult)
                }
                is Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is Effect.LoggedIn -> {
                    onNavigateToHome()
                }
                is Effect.LaunchPrivacyPolicyIntent -> {
                    context.launchPrivacyIntent()
                }
            }
        }
    }
}