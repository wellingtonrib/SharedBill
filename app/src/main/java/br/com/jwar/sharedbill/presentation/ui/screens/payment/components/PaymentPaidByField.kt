package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.generic_components.SelectDialog

@Composable
fun PaymentPaidByField(
    paidBySelection: MutableState<User>,
    members: List<User>
) {
    val isPaidBySelecting = remember { mutableStateOf(false) }
    if (isPaidBySelecting.value) {
        SelectDialog(
            title = "Paid By",
            message = "Select the payer",
            options = members.associateWith { it == paidBySelection.value },
            isMultiChoice = false,
            onDismiss = {
                isPaidBySelecting.value = false
            },
            onSelect = {
                isPaidBySelecting.value = false
                paidBySelection.value = it.first()
            }
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Paid by: ")
        OutlinedButton(
            onClick = { isPaidBySelecting.value = true },
            modifier = Modifier.weight(1f)
        )
        {
            Text(text = paidBySelection.value.name)
        }
    }
}