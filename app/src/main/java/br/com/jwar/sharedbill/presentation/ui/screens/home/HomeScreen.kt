package br.com.jwar.sharedbill.presentation.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen
import br.com.jwar.sharedbill.presentation.ui.screens.home.HomeContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.home.components.HomeContent

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    HomeContent(
        state = state,
        onSignOutClick = { viewModel.emitEvent { HomeContract.Event.SignOut } }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { HomeContract.Event.GetUser }
        viewModel.uiEffect.collect { action ->
            when (action) {
                is Effect.GoToAuth -> {
                    navController.navigate(AppScreen.Auth.route)
                }
            }
        }
    }
}