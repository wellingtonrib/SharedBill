package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.generic_components.EmptyContent
import br.com.jwar.sharedbill.presentation.ui.generic_components.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent

@Composable
fun GroupListContent(
    state: State,
    onGroupCreate: (String) -> Unit = {},
    onGroupJoin: (String) -> Unit = {},
    onGroupClick: (group: Group) -> Unit = {},
    onTryAgainClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GroupListHeader(onGroupCreate, onGroupJoin)
        when(state) {
            is State.Loading -> LoadingContent()
            is State.Empty -> EmptyContent(message = "No groups, create or join one")
            is State.Loaded -> GroupList(state.groups, onGroupClick)
            is State.Error -> ErrorContent(message = state.message.orEmpty(), action = onTryAgainClick)
        }
    }
}

@Preview
@Composable
fun PreviewGroupListContent() {
    SharedBillTheme {
        Scaffold {
            GroupListContent(
                state = State.Loaded(listOf(
                    Group(title = "Group One"),
                    Group(title = "Group Two"),
                    Group(title = "Group Three")
                ))
            )
        }
    }
}