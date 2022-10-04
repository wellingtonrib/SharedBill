package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group

@Composable
fun GroupsList(
    groups: List<Group>,
    onGroupClick: (group: Group) -> Unit,
) {
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