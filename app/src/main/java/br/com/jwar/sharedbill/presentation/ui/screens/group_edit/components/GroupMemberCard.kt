package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.User
import kotlinx.coroutines.launch

@Composable
fun GroupMemberCard(
    member: User,
    onMemberSelect: (member: User) -> Unit,
    onMemberDelete: (userId: String) -> Unit
) {
    val scope = rememberCoroutineScope()

    val confirmUserDeletion = remember { mutableStateOf(false) }
    val userDeletionDismissState = rememberDismissState(
        confirmStateChange = {
            if(it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                confirmUserDeletion.value = true
            }
            true
        }
    )

    if (confirmUserDeletion.value) {
        AlertDialog(
            onDismissRequest = { confirmUserDeletion.value = false },
            title = { Text("Confirm deletion") },
            text = {},
            confirmButton = {
                Button(
                    onClick = {
                        onMemberDelete(member.uid)
                        confirmUserDeletion.value = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        scope.launch { userDeletionDismissState.reset() }
                        confirmUserDeletion.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    SwipeToDismiss(
        state = userDeletionDismissState,
        background = {}
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { onMemberSelect(member) }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = member.name)
            }
        }
    }
}

