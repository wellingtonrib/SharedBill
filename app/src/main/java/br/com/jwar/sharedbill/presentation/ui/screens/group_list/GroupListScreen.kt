package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.components.GroupListContent

@Composable
fun GroupListScreen(
    navController: NavController,
    viewModel: GroupListViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    GroupListContent(
        state = state,
        onGroupCreate = {
            viewModel.emitEvent { Event.OnGroupCreate(it) }
        },
        onGroupJoin = {
            viewModel.emitEvent { Event.OnGroupJoin(it) }
        },
        onGroupClick = {
            viewModel.emitEvent { Event.OnGroupSelect(it) }
        },
        onTryAgainClick = {
            viewModel.emitEvent { Event.OnRequestGroups(true) }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnRequestGroups(true) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.OpenGroupDetails ->
                    navController.navigate(GroupDetails.createRoute(effect.groupId))
                is Effect.GoToAuth -> {
                    navController.popBackStack()
                    navController.navigate(AppScreen.Auth.route)
                }
            }
        }
    }
}