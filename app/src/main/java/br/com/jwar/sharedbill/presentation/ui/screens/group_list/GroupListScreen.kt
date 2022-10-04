package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect.OpenGroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect.OpenJoinGroup
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event.*
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
            viewModel.emitEvent { OnGroupCreate(it) }
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
                is OpenJoinGroup -> {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}