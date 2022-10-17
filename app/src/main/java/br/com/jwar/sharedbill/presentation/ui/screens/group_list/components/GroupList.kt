package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.presentation.ui.theme.paddingMedium

@Composable
fun GroupList(
    groups: List<GroupUiModel>,
    onGroupClick: (groupId: String) -> Unit,
) {
    LazyColumn(content = {
        items(groups) { group ->
            GroupsListItem(group, onGroupClick)
        }
    })
}

@Composable
fun GroupsListItem(group: GroupUiModel, onGroupClick: (groupId: String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidthPaddingMedium(),
        onClick = { onGroupClick(group.id) }
    ) {
        Column(
            modifier = Modifier.paddingMedium()
        ) {
            Text(text = group.title)
        }
    }
}