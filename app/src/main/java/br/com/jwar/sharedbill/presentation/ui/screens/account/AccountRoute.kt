package br.com.jwar.sharedbill.presentation.ui.screens.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.account.components.AccountScreen

@Composable
fun AccountRoute(
    viewModel: AccountViewModel = hiltViewModel(),
    onNavigateToAuth: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value

    AccountScreen(
        state = state,
        onSignOutClick = { viewModel.emitEvent { AccountContract.Event.OnSignOut } }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { AccountContract.Event.OnInit }
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is Effect.GoToAuth -> {
                    onNavigateToAuth()
                }
            }
        }
    }
}