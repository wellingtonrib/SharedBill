package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.components.GroupListContent

@Composable
fun GroupListScreen(
    snackbarHostState: SnackbarHostState,
    navigateToAuth: () -> Unit,
    navigateToGroupDetails: (String) -> Unit,
    navigateToGroupEdit: (String) -> Unit,
    viewModel: GroupListViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    GroupListContent(
        state = state,
        onGroupClick = {
            viewModel.emitEvent { Event.OnGroupSelect(it) }
        },
        onGroupCreate = {
            viewModel.emitEvent { Event.OnGroupCreate(it) }
        },
        onGroupJoin = {
            viewModel.emitEvent { Event.OnGroupJoin(it) }
        },
        onGroupDelete = {
            viewModel.emitEvent { Event.OnGroupDelete(it) }
        },
        onGroupLeave = {
            viewModel.emitEvent { Event.OnGroupLeave(it) }
        }
    ) {
        viewModel.emitEvent { Event.OnInit }
    }

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnInit }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.GoToAuth -> navigateToAuth()
                is Effect.OpenGroupDetails -> navigateToGroupDetails(effect.groupId)
                is Effect.OpenGroupEdit -> navigateToGroupEdit(effect.groupId)
                is Effect.Error -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message.asString(context)
                    )
                }
            }
        }
    }
}