package br.com.jwar.sharedbill.presentation.ui.screens.group_members.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.screens.group_members.GroupMembersContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.widgets.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent

@Composable
fun GroupMembersContent(
    state: State,
    onMemberClick: (member: User) -> Unit = {},
    onAddMemberClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state) {
            is State.Loading -> LoadingContent()
            is State.Loaded -> GroupMembersList(state.members, onMemberClick, onAddMemberClick)
            is State.Error -> ErrorContent()
        }
    }
}

@Composable
fun GroupMembersList(
    members: List<User>,
    onMemberClick: (member: User) -> Unit,
    onAddMemberClick: () -> Unit
) {
    Text(text = "Group Members", style = MaterialTheme.typography.titleLarge)
    LazyColumn(content = {
        items(members) { member ->
            GroupMembersCard(member, onMemberClick)
        }
    })
    Button(onClick = onAddMemberClick) {
        Text(text = "Add Member")
    }
}

@Composable
fun GroupMembersCard(member: User, onMemberClick: (member: User) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = { onMemberClick(member) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = member.name)
        }
    }
}

@Preview
@Composable
fun PreviewGroupMembersContent() {
    SharedBillTheme {
        Scaffold {
            GroupMembersContent(
                state = State.Loaded(listOf(
                    User("User One"),
                    User("User Two"),
                    User("User Three"),
                ))
            )
        }
    }
}
