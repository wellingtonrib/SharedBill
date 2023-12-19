package br.com.jwar.sharedbill.groups.presentation.ui.group_edit.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.components.InputDialog
import br.com.jwar.sharedbill.core.designsystem.theme.Icons
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun GroupEditFooter(
    listState: LazyListState = rememberLazyListState(),
    onSaveMemberClick: (String) -> Unit = {}
) {
    val expanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    var showInputDialog by remember { mutableStateOf(false) }

    if (showInputDialog) {
        InputDialog(
            label = stringResource(R.string.placeholder_user_name),
            placeholder = stringResource(R.string.placeholder_add_member_name),
            action = stringResource(DSR.string.label_save),
            onDismiss = { showInputDialog = false },
            onAction = { showInputDialog = false; onSaveMemberClick(it) },
            minWords = 2,
        )
    }
    ExtendedFloatingActionButton(
        onClick = { showInputDialog = true },
        expanded = expanded,
        icon = { Icon(painterResource(Icons.AddMember), stringResource(R.string.label_group_add_member)) },
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