package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State.Loaded
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State.Loading
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State.Error
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.widgets.EmptyContent
import br.com.jwar.sharedbill.presentation.ui.widgets.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent


@Composable
fun GroupListContent(
    state: State,
    onNewGroupClick: () -> Unit = {},
    onJoinGroupClick: () -> Unit = {},
    onGroupClick: (group: Group) -> Unit = {},
    onTryAgainClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Button(onClick = onNewGroupClick) {
                Text(text = "New Group")
            }
            Divider(modifier = Modifier.width(16.dp))
            Button(onClick = onJoinGroupClick) {
                Text(text = "Join a Group")
            }
        }
        when(state) {
            is Loading -> LoadingContent()
            is Loaded -> if (state.groups.isNotEmpty()) GroupsList(state.groups, onGroupClick)
                         else EmptyContent(action = null, message = "No groups, create or join one")
            is Error -> ErrorContent(action = onTryAgainClick)
        }
    }
}

@Composable
fun GroupsList(groups: List<Group>, onGroupClick: (group: Group) -> Unit) {
    LazyColumn(content = {
        items(groups) { group ->
            GroupsListItem(group, onGroupClick)
        }
    })
}

@Composable
fun GroupsListItem(group: Group, onGroupClick: (group: Group) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = { onGroupClick(group) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = group.title)
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