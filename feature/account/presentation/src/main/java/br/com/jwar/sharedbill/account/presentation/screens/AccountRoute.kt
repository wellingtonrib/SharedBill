package br.com.jwar.sharedbill.account.presentation.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.account.presentation.screens.AccountContract.Effect
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable
fun AccountRoute(
    viewModel: AccountViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val state = viewModel.uiState.collectAsState().value

    AccountScreen(
        state = state,
        onSignInClick = { viewModel.emitEvent { AccountContract.Event.OnRequestSignIn } },
        onSignOutClick = { viewModel.emitEvent { AccountContract.Event.OnSignOut } }
    )

    val launcherForActivityResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.emitEvent { AccountContract.Event.OnRequestSignInFirebase(result.data) }
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
            }
        }
    }
}