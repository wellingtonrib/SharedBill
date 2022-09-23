package br.com.jwar.sharedbill.presentation.ui.screens.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.account.components.AccountContent

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    AccountContent(
        state = state,
        onSignOutClick = { viewModel.emitEvent { AccountContract.Event.SignOut } }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { AccountContract.Event.GetUser }
        viewModel.uiEffect.collect { action ->
            when (action) {
                is Effect.GoToAuth -> {
                    navController.navigate(AppScreen.Auth.route)
                }
            }
        }
    }
}