package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.generic_components.InputDialog
import br.com.jwar.sharedbill.presentation.ui.theme.HorizontalSpacerMedium

@Composable
fun GroupListHeader(
    onGroupCreate: (String) -> Unit = {},
    onGroupJoin: (String) -> Unit = {},
) {
    val openGroupCreateDialog = GroupCreateDialog(onGroupCreate)
    val openGroupJoinDialog = GroupJoinDialog(onGroupJoin)

    Row {
        Button(onClick = { openGroupCreateDialog.value = true }) {
            Text(text = stringResource(R.string.label_group_new))
        }
        HorizontalSpacerMedium()
        Button(onClick = { openGroupJoinDialog.value = true }) {
            Text(text = stringResource(R.string.label_group_join))
        }
    }
}

@Composable
private fun GroupJoinDialog(onGroupJoin: (String) -> Unit): MutableState<Boolean> {
    val openGroupJoinDialog = remember { mutableStateOf(false) }
    if (openGroupJoinDialog.value) {
        InputDialog(
            label = stringResource(R.string.label_group_invite_code),
            action = stringResource(R.string.label_verify),
            onDismiss = { openGroupJoinDialog.value = false },
            onAction = { openGroupJoinDialog.value = false; onGroupJoin(it) }
        )
    }
    return openGroupJoinDialog
}

@Composable
private fun GroupCreateDialog(onGroupCreate: (String) -> Unit): MutableState<Boolean> {
    val openGroupCreateDialog = remember { mutableStateOf(false) }
    if (openGroupCreateDialog.value) {
        InputDialog(
            label = stringResource(R.string.label_group_title),
            placeholder = stringResource(R.string.placeholder_group_title),
            action = stringResource(R.string.label_save),
            onDismiss = { openGroupCreateDialog.value = false },
            onAction = { openGroupCreateDialog.value = false; onGroupCreate(it) }
        )
    }
    return openGroupCreateDialog
}