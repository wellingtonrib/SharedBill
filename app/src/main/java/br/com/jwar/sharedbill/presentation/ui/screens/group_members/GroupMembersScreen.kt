package br.com.jwar.sharedbill.presentation.ui.screens.group_members

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.ui.screens.group_members.components.GroupMembersContent
import br.com.jwar.sharedbill.presentation.ui.screens.group_members.GroupMembersContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_members.GroupMembersContract.Effect

@Composable
fun GroupMembersScreen(
    navController: NavController,
    viewModel: GroupMembersViewModel = hiltViewModel(),
    groupId: String,
) {
    val state = viewModel.uiState.collectAsState().value

    GroupMembersContent(
        state = state,
        onMemberClick = { viewModel.emitEvent { Event.OnMemberSelect } },
        onAddMemberClick = { viewModel.emitEvent { Event.OnAddMemberClick } }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnRequestMembers(groupId) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.OpenMemberDetails -> {
                    TODO("Not yet implemented")
                }
                is Effect.OpenMemberCreate -> {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}