package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun GroupsDetails(
    group: Group,
    onNewPaymentClick: ()-> Unit = {},
    onManageClick: ()-> Unit = {},
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                GroupInfo(group, onManageClick)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                GroupBalance(group)
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(group.payments) { payment ->
                GroupPaymentCard(payment, group)
            }
        }
        NewExpenseButton(onNewPaymentClick)
    }
}

@Composable
private fun NewExpenseButton(onNewPaymentClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onNewPaymentClick,
    ) {
        Text(text = "New expense")
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
