package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.generic_components.InputDialog
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun GroupEditFooter(
    listState: LazyListState = rememberLazyListState(),
    onSaveMemberClick: (String) -> Unit = {}
) {
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
    ExtendedFloatingActionButton(
        onClick = { openGroupAddMemberDialog.value = true },
        expanded = listState.firstVisibleItemIndex == 0,
        icon = { Icon(painterResource(R.drawable.ic_baseline_person_add_24), stringResource(R.string.label_group_add_member)) },
        text = { Text(text = stringResource(R.string.label_group_add_member)) },
    )
}

@Preview
@Composable
fun PreviewGroupEditFooter() {
    SharedBillTheme {
        GroupEditFooter()
    }
}