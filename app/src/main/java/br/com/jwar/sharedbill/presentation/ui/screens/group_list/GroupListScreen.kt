package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupCreate
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.components.GroupListContent
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event.OnNewGroup
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event.OnGroupSelected
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event.OnGetGroups
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect.OpenGroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect.OpenGroupCreate

@ExperimentalMaterial3Api
@Composable
fun GroupListScreen(
    navController: NavController,
    viewModel: GroupListViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    GroupListContent(
        state = state,
        onNewGroupClick = {
            viewModel.emitEvent { OnNewGroup }
        },
        onGroupClick = {
            viewModel.emitEvent { OnGroupSelected(it) }
        },
        onTryAgainClick = {
            viewModel.emitEvent { OnGetGroups(true) }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { OnGetGroups(true) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is OpenGroupDetails ->
                    navController.navigate(route = GroupDetails.createRoute(effect.groupId))
                is OpenGroupCreate -> {
                    navController.navigate(route = GroupCreate.route)
                }
            }
        }
    }
}