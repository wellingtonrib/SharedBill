package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jwar.sharedbill.core.DATE_FORMAT_SMALL
import br.com.jwar.sharedbill.core.format
import br.com.jwar.sharedbill.core.toCurrency
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.presentation.ui.generic_components.InfoDialog

@Composable
fun GroupPaymentCard(payment: Payment) {
    val showingPaymentInfo = remember { mutableStateOf(false) }
    if (showingPaymentInfo.value) {
        InfoDialog(
            image = null,
            title = "Payment Detail",
            message = payment.getDetail(),
            onDismiss = { showingPaymentInfo.value = false },
            onAction = { showingPaymentInfo.value = false }
        )
    }

    Column {
        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showingPaymentInfo.value = true
            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = payment.createdAt.format(DATE_FORMAT_SMALL))
                Divider(modifier = Modifier.width(16.dp), color = Color.Transparent)
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = payment.description)
                    Text(text = "Paid by ${payment.paidBy.name}", fontSize = 10.sp)
                }
                Divider(modifier = Modifier.width(16.dp), color = Color.Transparent)
                Text(text = payment.value.toCurrency())
            }
        }
        Divider(modifier = Modifier.height(16.dp), color = Color.Transparent)
    }
}