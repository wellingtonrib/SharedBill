package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
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
import br.com.jwar.sharedbill.presentation.ui.generic_components.VerticalSpacerMedium
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract
import br.com.jwar.sharedbill.presentation.ui.theme.*

@Composable
fun GroupEditForm(
    state: GroupEditContract.State.Editing,
    onSaveGroupClick: (group: GroupUiModel) -> Unit = {},
    onSaveMemberClick: (String) -> Unit = {},
    onMemberSelectionChange: (UserUiModel?) -> Unit = {},
    onMemberDeleteClick: (String) -> Unit = {},
) {
    SelectedMemberDialog(state, onMemberSelectionChange)
    LazyColumn(
        modifier = Modifier.fillMaxWidthPaddingMedium(),
    ) {
        item {
            GroupEditHeader(state.group, onSaveGroupClick)
        }
        item {
            VerticalSpacerMedium()
            GroupEditMembersHeader()
        }
        items(state.group.members) { member ->
            VerticalSpacerMedium()
            GroupMemberCard(
                member = member,
                onMemberSelect = { onMemberSelectionChange(it) },
                onMemberDelete = { onMemberDeleteClick(it) }
            )
        }
        item {
            VerticalSpacerMedium()
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
    onSaveGroupClick: (group: GroupUiModel) -> Unit
) {
    var groupEdited by remember { mutableStateOf(group) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(R.string.label_group_info)
        )
        Button(onClick = { onSaveGroupClick(groupEdited) }) {
            Text(text = stringResource(R.string.label_save))
        }
    }
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
        Spacer(modifier = Modifier.width(AppTheme.dimens.space_8))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = MaterialTheme.shapes.medium,
            value = groupEdited.title,
            label = { Text(text = stringResource(R.string.label_group_title)) },
            placeholder = { Text(text = stringResource(R.string.placeholder_group_title)) },
            onValueChange = { groupEdited = groupEdited.copy(title = it) }
        )
    }
}

@Composable
private fun GroupEditFooter(onSaveMemberClick: (String) -> Unit,) {
    val openGroupAddMemberDialog = remember { mutableStateOf(false) }
    if (openGroupAddMemberDialog.value) {
        InputDialog(
            label = stringResource(R.string.label_add_member_name),
            placeholder = stringResource(R.string.placeholder_add_member_name),
            action = stringResource(R.string.label_save),
            onDismiss = { openGroupAddMemberDialog.value = false },
            onAction = { openGroupAddMemberDialog.value = false; onSaveMemberClick(it) }
        )
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(onClick = { openGroupAddMemberDialog.value = true }) {
            Text(text = stringResource(R.string.label_add_member))
        }
        Spacer(modifier = Modifier.horizontalSpaceMedium())
        Button(onClick = { }) {
            Text(text = stringResource(R.string.label_invite_link))
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