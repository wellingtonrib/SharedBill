@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Loaded
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Loading
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Error
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.widgets.EmptyContent
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent

@ExperimentalMaterial3Api
@Composable
fun GroupDetailsContent(
    state: State,
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp),) {
        when(state) {
            is Loading -> LoadingContent()
            is Loaded -> GroupsDetails(state.group)
            is Error -> EmptyContent()
        }
    }
}

@Composable
fun GroupsDetails(group: Group) {
    Column {
        Text(text = "Group Details", style = MaterialTheme.typography.titleLarge)
        Text(text = "Title: ${group.title}")
    }
}

@Preview
@Composable
fun PreviewGroupDetailsContent() {
    SharedBillTheme {
        Scaffold {
            GroupDetailsContent(state = Loaded(
                Group(title = "Group One"))
            )
        }
    }
}
