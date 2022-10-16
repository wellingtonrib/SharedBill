package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.paddingMedium
import kotlinx.coroutines.launch

@Composable
fun GroupMemberCard(
    member: UserUiModel,
    onMemberSelect: (member: UserUiModel) -> Unit,
    onMemberDelete: (userId: String) -> Unit
) {
    val userDeletionState = GetUserDeletionState(member, onMemberDelete)

    SwipeToDismiss(
        state = userDeletionState,
        background = {}
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onMemberSelect(member) }
        ) {
            Column(
                modifier = Modifier.paddingMedium()
            ) {
                Text(text = member.name)
            }
        }
    }
}

@Composable
private fun GetUserDeletionState(
    member: UserUiModel,
    onMemberDelete: (userId: String) -> Unit
): DismissState {
    val scope = rememberCoroutineScope()
    val confirmUserDeletion = remember { mutableStateOf(false) }
    val userDeletionDismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                confirmUserDeletion.value = true
            }
            true
        }
    )

    if (confirmUserDeletion.value) {
        AlertDialog(
            onDismissRequest = { confirmUserDeletion.value = false },
            title = { Text(stringResource(R.string.label_confirm)) },
            text = { Text(stringResource(R.string.message_confirm_member_deletion)) },
            confirmButton = {
                Button(
                    onClick = {
                        onMemberDelete(member.uid)
                        confirmUserDeletion.value = false
                    }
                ) {
                    Text(stringResource(R.string.label_yes))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        scope.launch { userDeletionDismissState.reset() }
                        confirmUserDeletion.value = false
                    }
                ) {
                    Text(stringResource(R.string.label_cancel))
                }
            }
        )
    }
    return userDeletionDismissState
}

