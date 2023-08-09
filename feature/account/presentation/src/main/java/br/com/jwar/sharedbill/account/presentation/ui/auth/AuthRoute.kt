package br.com.jwar.sharedbill.account.presentation.ui.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.account.presentation.ui.auth.AuthContract.Effect
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable
fun AuthRoute(
    viewModel: AuthViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNavigateToHome: () -> Unit,
) {
    val state = viewModel.uiState.collectAsState().value

    AuthScreen(
        state = state,
        onSignInClick = { viewModel.emitEvent { AuthContract.Event.OnRequestSignIn } },
    )

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
                is Effect.LaunchSignInResult -> {
                    launchSignInResult(effect.signInResult)
                }
                is Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is Effect.LoggedIn -> {
                    onNavigateToHome()
                }
            }
        }
    }
}