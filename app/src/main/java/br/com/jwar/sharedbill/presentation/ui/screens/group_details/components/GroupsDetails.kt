package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group

@Composable
fun GroupsDetails(group: Group, onNewExpenseClick: ()-> Unit, onManageClick: ()-> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Group Details",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = onManageClick) {
                Text(text = "Manage")
            }
        }
        Text(text = "Title: ${group.title}")
        Text(text = "Members: ${group.members.joinToString { it.name }}")
        Divider(modifier = Modifier.height(16.dp), color = Color.Transparent)
        GroupPaymentsList(group, onNewExpenseClick)
    }
}
