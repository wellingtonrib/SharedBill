package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import br.com.jwar.sharedbill.core.toCurrency
import br.com.jwar.sharedbill.domain.model.Group
import java.math.BigDecimal

@Composable
fun GroupBalance(group: Group) {
    Column {
        Text(
            text = "Group Balance:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        group.balance.forEach { entry ->
            Row {
                val member = group.findMemberById(entry.key)
                val balance = entry.value.toBigDecimal()
                Text(text = "${member?.name ?: "Unknown"}: ")
                Text(
                    text = getBalanceText(balance),
                    color = getBalanceColor(balance)
                )
            }
        }
    }
}

@Composable
private fun getBalanceText(balance: BigDecimal) =
    if (balance > BigDecimal.ZERO) "Owes ${balance.toCurrency()}"
    else "Is owned ${balance.abs().toCurrency()}"

@Composable
private fun getBalanceColor(balance: BigDecimal) =
    if (balance > BigDecimal.ZERO) MaterialTheme.colorScheme.error
    else MaterialTheme.colorScheme.primary