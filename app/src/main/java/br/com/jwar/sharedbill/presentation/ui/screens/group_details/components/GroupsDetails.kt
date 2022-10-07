package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun GroupsDetails(
    group: Group,
    onNewPaymentClick: ()-> Unit = {},
    onManageClick: ()-> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Group Balance:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        GroupBalance(group)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Group Expenses:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        GroupPaymentsList(group, onNewPaymentClick)
    }
}

@Preview
@Composable
fun PreviewGroupDetails() {
    SharedBillTheme {
        Scaffold {
            GroupsDetails(Group.fake())
        }
    }
}
