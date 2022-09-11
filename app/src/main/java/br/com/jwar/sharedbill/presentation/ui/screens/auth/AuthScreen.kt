package br.com.jwar.sharedbill.presentation.ui.screens.auth

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.auth.components.AuthContent
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val snackHostState = remember { SnackbarHostState() }

    AuthContent(
        state = state,
        snackHostState = snackHostState,
        onSignInClick = { viewModel.emitEvent { Event.SignIn } }
    )

    val launcherForActivityResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.emitEvent { Event.SignInFirebase(result.data) }
        }
    }

    fun launchSignInResult(signInResult: BeginSignInResult) {
        launcherForActivityResult.launch(
            IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        )
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { action ->
            when (action) {
                is Effect.GoToHome -> {
                    navController.navigate(AppScreen.Home.route)
                }
                is Effect.LaunchSignInResult -> {
                    launchSignInResult(action.signInResult)
                }
                is Effect.ShowError -> {
                    snackHostState.showSnackbar(action.message)
                }
            }
        }
    }
}