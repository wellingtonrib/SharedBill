package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import br.com.jwar.sharedbill.core.toCurrency
import br.com.jwar.sharedbill.domain.model.Group
import java.math.BigDecimal

@Composable
fun GroupBalance(group: Group) {
    Column {
        group.balance.forEach { entry ->
            Row {
                val member = group.findMemberById(entry.key)
                val balance = entry.value.toBigDecimal()
                Text(text = "${member?.name ?: "Unknown"}: ")
                Text(
                    text = balance.toCurrency(),
                    color = if (balance > BigDecimal.ZERO) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}