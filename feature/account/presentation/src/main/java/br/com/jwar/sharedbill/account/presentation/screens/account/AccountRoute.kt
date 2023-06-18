package br.com.jwar.sharedbill.account.presentation.screens.account

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.account.presentation.screens.account.AccountContract.Effect

@Composable
fun AccountRoute(
    viewModel: AccountViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNavigateToAuth: () -> Unit,
) {
    val state = viewModel.uiState.collectAsState().value

    AccountScreen(
        state = state,
        onSignOutClick = { viewModel.emitEvent { AccountContract.Event.OnSignOut } }
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is Effect.NavigateToAuth -> {
                    onNavigateToAuth()
                }
            }
        }
    }
}