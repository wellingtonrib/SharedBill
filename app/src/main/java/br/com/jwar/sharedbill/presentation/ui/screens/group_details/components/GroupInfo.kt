package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import br.com.jwar.sharedbill.domain.model.Group

@Composable
fun GroupInfo(
    group: Group,
    onManageClick: () -> Unit
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Group Details:",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
            Button(onClick = onManageClick) {
                Text(text = "Manage")
            }
        }
        Row {
            Text(text = "Title: ", fontWeight = FontWeight.Bold)
            Text(text = group.title)
        }
        Row {
            Text(text = "Members: ", fontWeight = FontWeight.Bold)
            Text(text = group.members.joinToString { it.name })
        }
    }
}