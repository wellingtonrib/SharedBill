package br.com.jwar.sharedbill.presentation.ui.screens.auth

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
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
        onSignInClick = { viewModel.emitEvent { Event.OnSignIn } }
    )

    val launcherForActivityResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.emitEvent { Event.OnSignInFirebase(result.data) }
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
                    navController.navigate(AppScreen.Account.route) {
                        navController.currentBackStackEntry?.destination?.route?.let {
                            popUpTo(it) {
                                inclusive =  true
                            }
                        }
                    }
                }
                is Effect.LaunchSignInResult -> {
                    launchSignInResult(effect.signInResult)
                }
                is Effect.ShowError -> {
                    snackHostState.showSnackbar(effect.message)
                }
            }
        }
    }
}