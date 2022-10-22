package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.generic_components.InfoDialog
import br.com.jwar.sharedbill.presentation.ui.generic_components.InputDialog
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.presentation.ui.theme.horizontalSpaceMedium
import br.com.jwar.sharedbill.presentation.ui.theme.sizeLarge
import br.com.jwar.sharedbill.presentation.ui.theme.sizeMedium
import br.com.jwar.sharedbill.presentation.ui.theme.verticalSpaceMedium

@Composable
fun GroupEditForm(
    state: GroupEditContract.State.Editing,
    onGroupUpdated: (GroupUiModel) -> Unit = {},
    onSaveMemberClick: (String) -> Unit = {},
    onMemberSelectionChange: (UserUiModel?) -> Unit = {},
    onMemberDeleteClick: (String) -> Unit = {},
) {
    SelectedMemberDialog(state, onMemberSelectionChange)
    LazyColumn(
        modifier = Modifier.fillMaxWidthPaddingMedium(),
    ) {
        item {
            GroupEditHeader(state.group, onGroupUpdated)
        }
        item {
            Spacer(modifier = Modifier.verticalSpaceMedium())
            GroupEditMembersHeader()
        }
        items(state.group.members) { member ->
            Spacer(modifier = Modifier.verticalSpaceMedium())
            GroupMemberCard(
                member = member,
                onMemberSelect = { onMemberSelectionChange(it) },
                onMemberDelete = { onMemberDeleteClick(it) }
            )
        }
        item {
            Spacer(modifier = Modifier.verticalSpaceMedium())
            GroupEditFooter(onSaveMemberClick)
        }
    }
}

@Composable
private fun SelectedMemberDialog(
    state: GroupEditContract.State.Editing,
    onMemberSelectionChange: (UserUiModel?) -> Unit
) {
    state.selectedMember?.let { user ->
        InfoDialog(
            image = R.drawable.ic_baseline_account_circle_24,
            title = user.name,
            message = user.getJoinInfo(),
            action = stringResource(R.string.label_ok),
            onDismiss = { onMemberSelectionChange(null) },
            onAction = { onMemberSelectionChange(null) }
        )
    }
}

@Composable
private fun GroupEditMembersHeader() {
    Text(
        style = MaterialTheme.typography.titleLarge,
        text = stringResource(R.string.label_group_members)
    )
}

@Composable
private fun GroupEditHeader(
    group: GroupUiModel,
    onGroupUpdated: (GroupUiModel) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .sizeLarge()
            .background(AppTheme.colors.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.sizeMedium(),
                imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_groups_24),
                tint = Color.White,
                contentDescription = stringResource(R.string.description_group_image)
            )
        }
        Spacer(modifier = Modifier.horizontalSpaceMedium())
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = MaterialTheme.shapes.medium,
            value = group.title,
            label = { Text(text = stringResource(R.string.label_group_title)) },
            placeholder = { Text(text = stringResource(R.string.placeholder_group_title)) },
            onValueChange = { onGroupUpdated(group.copy(title = it)) }
        )
    }
}

@Composable
private fun GroupEditFooter(onSaveMemberClick: (String) -> Unit,) {
    val openGroupAddMemberDialog = remember { mutableStateOf(false) }
    if (openGroupAddMemberDialog.value) {
        InputDialog(
            label = stringResource(R.string.label_user_name),
            placeholder = stringResource(R.string.placeholder_add_member_name),
            action = stringResource(R.string.label_save),
            onDismiss = { openGroupAddMemberDialog.value = false },
            onAction = { openGroupAddMemberDialog.value = false; onSaveMemberClick(it) }
        )
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(onClick = { openGroupAddMemberDialog.value = true }) {
            Text(text = stringResource(R.string.label_group_add_member))
        }
        Spacer(modifier = Modifier.horizontalSpaceMedium())
        Button(onClick = { }) {
            Text(text = stringResource(R.string.label_group_invite_link))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupEditForm() {
    MaterialTheme {
        GroupEditForm(
            state = GroupEditContract.State.Editing(
                group = GroupUiModel.sample()
            )
        )
    }
}