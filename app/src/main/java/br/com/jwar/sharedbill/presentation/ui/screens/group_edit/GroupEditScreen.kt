package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components.GroupEditContent

@Composable
fun GroupEditScreen(
    navController: NavController,
    viewModel: GroupEditViewModel = hiltViewModel(),
    groupId: String
) {
    val state = viewModel.uiState.collectAsState().value
    val snackHostState = remember { SnackbarHostState() }

    GroupEditContent(
        state = state,
        snackHostState = snackHostState,
        onSaveGroupClick = { group ->
            viewModel.emitEvent { Event.OnSaveGroupClick(group) }
        },
        onSaveMemberClick = { userName ->
            viewModel.emitEvent { Event.OnSaveMemberClick(userName, groupId) }
        },
        onMemberSelectionChange = { user ->
            viewModel.emitEvent { Event.OnMemberSelectionChange(user) }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnRequestEdit(groupId) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.OpenGroupSaved -> {
                    with(navController) {
                        popBackStack()
                        navigate(GroupDetails.createRoute(effect.group.id))
                    }
                }
                is Effect.ShowError -> snackHostState.showSnackbar(effect.message)
            }
        }
    }
}