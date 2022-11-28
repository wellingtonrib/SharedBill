package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.components.GroupListContent

@Composable
fun GroupListScreen(
    navigateToAuth: () -> Unit,
    navigateToGroupDetails: (String) -> Unit,
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
            viewModel.emitEvent { Event.OnInit }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnInit }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.OpenGroupDetails ->
                    navigateToGroupDetails(effect.groupId)
                is Effect.GoToAuth -> {
                    navigateToAuth()
                }
            }
        }
    }
}