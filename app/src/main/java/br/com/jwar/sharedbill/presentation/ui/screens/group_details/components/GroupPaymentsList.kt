package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jwar.sharedbill.core.format
import br.com.jwar.sharedbill.core.toCurrency
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment

@Composable
fun GroupPaymentsList(
    group: Group,
    onNewExpenseClick: () -> Unit
) {
    Column {
        LazyColumn {
            items(group.payments) {
                GroupPaymentCard(it)
            }
        }
        Button(
            onClick = onNewExpenseClick,
            modifier = Modifier.Companion.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "New expense")
        }
    }
}

@Composable
fun GroupPaymentCard(payment: Payment) {
    Column {
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = payment.createdAt.format("dd/MM"))
                Divider(modifier = Modifier.width(16.dp), color = Color.Transparent)
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = payment.description)
                    Text(text = "Paid by ${payment.paidBy}", fontSize = 10.sp)
                }
                Divider(modifier = Modifier.width(16.dp), color = Color.Transparent)
                Text(text = payment.value.toCurrency())
            }
        }
        Divider(modifier = Modifier.height(16.dp), color = Color.Transparent)
    }
}