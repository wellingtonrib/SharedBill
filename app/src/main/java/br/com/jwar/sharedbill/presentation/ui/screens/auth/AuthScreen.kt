package br.com.jwar.sharedbill.presentation.ui.screens.auth

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.auth.components.AuthContent
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable
fun AuthScreen(
    navigateToHome: () -> Unit = {},
    snackbarHostState: SnackbarHostState,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    AuthContent(
        state = state,
        onSignInClick = { viewModel.emitEvent { Event.OnRequestSignIn } }
    )

    val launcherForActivityResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.emitEvent { Event.OnRequestSignInFirebase(result.data) }
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
                is Effect.GoToHome -> {
                    navigateToHome()
                }
                is Effect.LaunchSignInResult -> {
                    launchSignInResult(effect.signInResult)
                }
                is Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }
}