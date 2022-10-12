package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import br.com.jwar.sharedbill.presentation.ui.generic_components.VerticalSpacerMedium
import java.util.*

@Composable
fun PaymentForm(
    group: Group,
    currentMember: User,
    onSendPaymentClick: (PaymentContract.Event.SendPaymentParams) -> Unit
) {
    val description = remember { mutableStateOf("") }
    val value = remember { mutableStateOf("") }
    val date = remember { mutableStateOf(Date()) }
    val paidBySelection = remember { mutableStateOf(currentMember) }
    val paidToSelection = remember { mutableStateOf(group.members) }

    Column {
        PaymentDescriptionField(description = description)
        VerticalSpacerMedium()
        PaymentValueField(value = value)
        VerticalSpacerMedium()
        PaymentDateField(date = date)
        VerticalSpacerMedium()
        PaymentPaidByField(paidBySelection = paidBySelection, group.members)
        VerticalSpacerMedium()
        PaymentPaidToField(paidToSelection = paidToSelection, group.members)
        VerticalSpacerMedium()
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onSendPaymentClick(
                    PaymentContract.Event.SendPaymentParams(
                        description = description.value,
                        value = value.value,
                        paidBy = paidBySelection.value,
                        paidTo = paidToSelection.value,
                        date = date.value,
                        group = group,
                        currentMember = currentMember
                    )
                )
            }
        ) {
            Text(text = "Send payment")
        }
    }
}

