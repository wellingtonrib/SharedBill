package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.components.GroupDetailsContent
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Effect

@Composable
fun GroupDetailsScreen(
    navController: NavController,
    groupId: String,
    viewModel: GroupDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    GroupDetailsContent(
        state = state,
        onNewExpenseClick = {},
        onManageClick = { viewModel.emitEvent { Event.OnManageClick } }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnRequestGroup(groupId) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.OpenGroupMembers -> {
                    navController.navigate(AppScreen.GroupEdit.createRoute(groupId))
                }
            }
        }
    }
}