package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.presentation.ui.generic_components.InputDialog

@Composable
fun GroupListHeader(
    onGroupCreate: (String) -> Unit = {},
    onGroupJoin: (String) -> Unit = {},
) {
    val openGroupCreateDialog = remember { mutableStateOf(false) }
    if (openGroupCreateDialog.value) {
        InputDialog(
            label = "Enter a group name",
            placeholder = "Ex. Trip",
            action = "Save",
            onDismiss = { openGroupCreateDialog.value = false },
            onAction = { openGroupCreateDialog.value = false; onGroupCreate(it) }
        )
    }

    val openGroupJoinDialog = remember { mutableStateOf(false) }
    if (openGroupJoinDialog.value) {
        InputDialog(
            label = "Enter code",
            placeholder = "",
            action = "Verify",
            onDismiss = { openGroupJoinDialog.value = false },
            onAction = { openGroupJoinDialog.value = false; onGroupJoin(it) }
        )
    }

    Row {
        Button(onClick = { openGroupCreateDialog.value = true }) {
            Text(text = "New Group")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = { openGroupJoinDialog.value = true }) {
            Text(text = "Join a Group")
        }
    }
}