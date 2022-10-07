package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State.Error
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State.Loaded
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State.Loading
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.widgets.EmptyContent
import br.com.jwar.sharedbill.presentation.ui.widgets.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.widgets.InputDialog
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent


@Composable
fun GroupListContent(
    state: State,
    onGroupCreate: (String) -> Unit = {},
    onGroupJoin: (String) -> Unit = {},
    onGroupClick: (group: Group) -> Unit = {},
    onTryAgainClick: () -> Unit = {},
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Button(onClick = {  openGroupCreateDialog.value = true }) {
                Text(text = "New Group")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { openGroupJoinDialog.value = true }) {
                Text(text = "Join a Group")
            }
        }
        when(state) {
            is Loading -> LoadingContent()
            is Loaded -> if (state.groups.isNotEmpty()) GroupsList(state.groups, onGroupClick)
                         else EmptyContent(action = null, message = "No groups, create or join one")
            is Error -> ErrorContent(message = state.message.orEmpty(), action = onTryAgainClick)
        }
    }
}

@Preview
@Composable
fun PreviewGroupListContent() {
    SharedBillTheme {
        Scaffold {
            GroupListContent(
                state = Loaded(listOf(
                    Group(title = "Group One"),
                    Group(title = "Group Two"),
                    Group(title = "Group Three")
                ))
            )
        }
    }
}