package br.com.jwar.sharedbill.groups.presentation.ui.list

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.groups.presentation.ui.list.GroupListContract.Effect
import br.com.jwar.sharedbill.groups.presentation.ui.list.GroupListContract.Event

const val GROUP_LIST_ROUTE = "group_list"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GroupListRoute(
    viewModel: GroupListViewModel = hiltViewModel(),
    onNavigateToGroupDetails: (String) -> Unit,
    onNavigateToGroupEdit: (String) -> Unit,
    onNavigateToAuth: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        GroupListScreen(
            state = state,
            snackbarHostState = snackbarHostState,
            onGroupClick = { viewModel.emitEvent { Event.OnGroupSelect(it) } },
            onGroupCreate = { viewModel.emitEvent { Event.OnGroupCreate(it) } },
            onGroupJoin = { viewModel.emitEvent { Event.OnGroupJoin(it) } },
            onGroupDelete = { viewModel.emitEvent { Event.OnGroupDelete(it) } },
            onGroupLeave = { viewModel.emitEvent { Event.OnGroupLeave(it) } }
        ) { event -> viewModel.emitEvent { event } }
    }

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnInit }
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is Effect.NavigateToAuth -> onNavigateToAuth()
                is Effect.NavigateToGroupDetails -> onNavigateToGroupDetails(effect.groupId)
                is Effect.NavigateToGroupEdit -> onNavigateToGroupEdit(effect.groupId)
                is Effect.ShowError -> snackbarHostState.showSnackbar(effect.message.asString(context))
            }
        }
    }
}
