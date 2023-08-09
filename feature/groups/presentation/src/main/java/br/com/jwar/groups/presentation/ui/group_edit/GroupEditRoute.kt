package br.com.jwar.groups.presentation.ui.group_edit

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.groups.presentation.ui.group_edit.GroupEditContract.Effect
import br.com.jwar.groups.presentation.ui.group_edit.GroupEditContract.Event
import br.com.jwar.sharedbill.core.utility.extensions.shareText
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupEditRoute(
    groupId: String,
    viewModel: GroupEditViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNavigateToDetails: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    GroupEditScreen(
        state = state,
        onGroupUpdated = { group -> viewModel.emitEvent { Event.OnGroupUpdated(group) } },
        onSaveMemberClick = { userName -> viewModel.emitEvent { Event.OnSaveMember(userName, groupId) } },
        onMemberSelectionChange = { user -> viewModel.emitEvent { Event.OnMemberSelect(user) } },
        onMemberDeleteClick = { userId -> viewModel.emitEvent { Event.OnMemberDelete(userId, groupId) } },
        onShareInviteCodeClick = { inviteCode -> viewModel.emitEvent { Event.OnShareInviteCode(inviteCode) } },
        onSaveClick = { viewModel.emitEvent { Event.OnSaveGroup } },
        onNavigateBack = onNavigateBack
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.ShowError ->
                    snackbarHostState.showSnackbar(effect.message.asString(context))
                is Effect.ShowSuccess ->
                    snackbarHostState.showSnackbar(context.getString(R.string.message_group_saved_success))
                is Effect.NavigateToGroupDetails ->
                    onNavigateToDetails(groupId)
                is Effect.ShareInviteCode ->
                    shareInviteCode(context, effect.inviteCode)
            }
        }
    }
}

fun shareInviteCode(context: Context, inviteCode: String) {
    context.shareText(context.getString(R.string.message_share_invite_code, inviteCode))
}
