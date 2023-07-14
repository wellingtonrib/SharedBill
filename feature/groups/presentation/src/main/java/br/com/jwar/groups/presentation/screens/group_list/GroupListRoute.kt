package br.com.jwar.groups.presentation.screens.group_list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.groups.presentation.screens.group_list.GroupListContract.Effect
import br.com.jwar.groups.presentation.screens.group_list.GroupListContract.Event

const val GROUP_LIST_ROUTE = "group_list"

@Composable
fun GroupListRoute(
    viewModel: GroupListViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNavigateToGroupDetails: (String) -> Unit,
    onNavigateToGroupEdit: (String) -> Unit,
    onNavigateToAuth: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    GroupListScreen(
        state = state,
        onGroupClick = { viewModel.emitEvent { Event.OnGroupSelect(it) } },
        onGroupCreate = { viewModel.emitEvent { Event.OnGroupCreate(it) } },
        onGroupJoin = { viewModel.emitEvent { Event.OnGroupJoin(it) } },
        onGroupDelete = { viewModel.emitEvent { Event.OnGroupDelete(it) } },
        onGroupLeave = { viewModel.emitEvent { Event.OnGroupLeave(it) } },
        onTryAgainClick = { event -> viewModel.emitEvent{ event } }
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.NavigateToAuth -> onNavigateToAuth()
                is Effect.NavigateToGroupDetails -> onNavigateToGroupDetails(effect.groupId)
                is Effect.NavigateToGroupEdit -> onNavigateToGroupEdit(effect.groupId)
                is Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message.asString(context))
                }
            }
        }
    }
}