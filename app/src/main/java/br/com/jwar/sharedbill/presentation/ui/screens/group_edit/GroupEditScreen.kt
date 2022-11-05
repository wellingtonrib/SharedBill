package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupDetails
import br.com.jwar.sharedbill.presentation.navigation.AppTopBar
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components.GroupEditContent

@Composable
fun GroupEditScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: GroupEditViewModel = hiltViewModel(),
    groupId: String
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    Column {
        AppTopBar(
            navController = navController,
            title = stringResource(id = R.string.label_group_edit),
            actions = {
                IconButton(onClick = {
                    viewModel.emitEvent { Event.OnSaveGroupClick }
                }) {
                    Icon(Icons.Filled.Done, stringResource(id = R.string.label_group_manage))
                }
            }
        )
        GroupEditContent(
            state = state,
            onGroupUpdated = { group ->
                viewModel.emitEvent { Event.OnGroupUpdated(group) }
            },
            onSaveMemberClick = { userName ->
                viewModel.emitEvent { Event.OnSaveMemberClick(userName, groupId) }
            },
            onMemberSelectionChange = { user ->
                viewModel.emitEvent { Event.OnMemberSelectionChange(user) }
            },
        ) { userId ->
            viewModel.emitEvent { Event.OnMemberDeleteClick(userId, groupId) }
        }
    }

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
                is Effect.ShowError ->
                    snackbarHostState.showSnackbar(
                        effect.message.ifEmpty { context.getString(R.string.error_generic) }
                    )
            }
        }
    }
}