package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupEdit
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.components.GroupListContent
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event.OnNewGroupClick
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event.OnGroupSelect
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event.OnJoinAGroupClick
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event.OnRequestGroups
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect.OpenGroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect.OpenGroupCreate
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect.OpenJoinGroup

@Composable
fun GroupListScreen(
    navController: NavController,
    viewModel: GroupListViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    GroupListContent(
        state = state,
        onNewGroupClick = {
            viewModel.emitEvent { OnNewGroupClick }
        },
        onJoinGroupClick = {
            viewModel.emitEvent { OnJoinAGroupClick }
        },
        onGroupClick = {
            viewModel.emitEvent { OnGroupSelect(it) }
        },
        onTryAgainClick = {
            viewModel.emitEvent { OnRequestGroups(true) }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { OnRequestGroups(true) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is OpenGroupDetails ->
                    navController.navigate(route = GroupDetails.createRoute(effect.groupId))
                is OpenGroupCreate -> {
                    navController.navigate(route = GroupEdit.route)
                }
                is OpenJoinGroup -> {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}