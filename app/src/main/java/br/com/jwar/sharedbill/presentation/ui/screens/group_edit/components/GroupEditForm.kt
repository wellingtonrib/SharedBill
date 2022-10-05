package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract
import br.com.jwar.sharedbill.presentation.ui.widgets.InfoDialog
import br.com.jwar.sharedbill.presentation.ui.widgets.InputDialog

@Composable
fun GroupEditForm(
    state: GroupEditContract.State.Editing,
    onSaveGroupClick: (group: Group) -> Unit,
    onSaveMemberClick: (String) -> Unit,
    onMemberSelectionChange: (User?) -> Unit,
) {
    var groupEdited by remember { mutableStateOf(state.group) }

    val openGroupAddMemberDialog = remember { mutableStateOf(false) }
    if (openGroupAddMemberDialog.value) {
        InputDialog(
            label = "Enter user name",
            placeholder = "Ex. Alex",
            action = "Save",
            onDismiss = { openGroupAddMemberDialog.value = false },
            onAction = { openGroupAddMemberDialog.value = false; onSaveMemberClick(it) }
        )
    }

    state.selectedMember?.let { user ->
        InfoDialog(
            image = R.drawable.ic_baseline_account_circle_24,
            title = user.name,
            message = "Invite code: ${user.inviteCode.orEmpty()}",
            action = "Ok",
            onDismiss = { onMemberSelectionChange(null) },
            onAction = { onMemberSelectionChange(null) }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge,
                    text = "Group Info"
                )
                Button(onClick = { onSaveGroupClick(groupEdited) }) {
                    Text(text = "Save")
                }
            }
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .padding(12.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_groups_24),
                    contentDescription = "Group Image"
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    value = state.group.title,
                    label = { Text(text = "Group Name") },
                    placeholder = { Text(text = "Ex: Trip") },
                    onValueChange = { groupEdited = groupEdited.copy(title = it) }
                )
            }
        }
        item {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Group Members"
            )
        }
        items(state.group.members) { member ->
            GroupMemberCard(member, onMemberSelect = { onMemberSelectionChange(it) })
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { openGroupAddMemberDialog.value = true }) {
                    Text(text = "Add Member")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { }) {
                    Text(text = "Invite Link")
                }
            }
        }
    }
}