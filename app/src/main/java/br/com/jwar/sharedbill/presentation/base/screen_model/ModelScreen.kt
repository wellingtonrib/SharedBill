package br.com.jwar.sharedbill.presentation.base.screen_model

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.Event
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.Effect
import br.com.jwar.sharedbill.presentation.base.screen_model.components.ModelContent

@ExperimentalMaterial3Api
@Composable
fun ModelScreen(
    navController: NavController,
    viewModel: ModelViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    ModelContent(
        state = state,
        onClickSomething = { TODO("Not yet implemented") }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnLoad }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.OpenNext -> {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}