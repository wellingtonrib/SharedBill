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
fun PaymentPaidToField(
    paidToSelection: MutableState<List<User>>,
    members: List<User>
) {
    val isPaidToSelecting = remember { mutableStateOf(false) }
    if (isPaidToSelecting.value) {
        SelectDialog(
            title = "Paid To",
            message = "Select the members",
            options = members.associateWith { paidToSelection.value.contains(it) },
            onDismiss = {
                isPaidToSelecting.value = false
            },
            onSelect = {
                isPaidToSelecting.value = false
                paidToSelection.value = it
            }
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Paid to: ")
        OutlinedButton(
            onClick = { isPaidToSelecting.value = true },
            modifier = Modifier.weight(1f)
        )
        {
            Text(text = paidToSelection.value.joinToString { it.name })
        }
    }
}