package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import br.com.jwar.sharedbill.presentation.ui.theme.verticalSpaceMedium
import java.util.Date

@Composable
fun PaymentForm(
    params: PaymentContract.SendPaymentParams,
    onSendPaymentClick: (PaymentContract.SendPaymentParams) -> Unit
) {
    val description = remember { mutableStateOf(params.description) }
    val value = remember { mutableStateOf(params.value) }
    val date = remember { mutableStateOf(Date()) }
    val paidBySelection = remember { mutableStateOf(params.paidBy) }
    val paidToSelection = remember { mutableStateOf(params.paidTo) }

    Column {
        PaymentDescriptionField(description = description)
        Spacer(modifier = Modifier.verticalSpaceMedium())
        PaymentValueField(value = value)
        Spacer(modifier = Modifier.verticalSpaceMedium())
        PaymentDateField(date = date)
        Spacer(modifier = Modifier.verticalSpaceMedium())
        PaymentPaidByField(paidBySelection = paidBySelection, params.group.members)
        Spacer(modifier = Modifier.verticalSpaceMedium())
        PaymentPaidToField(paidToSelection = paidToSelection, params.group.members)
        Spacer(modifier = Modifier.verticalSpaceMedium())
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onSendPaymentClick(
                    params.copy(
                        description = description.value,
                        value = value.value,
                        paidBy = paidBySelection.value,
                        paidTo = paidToSelection.value,
                        date = date.value,
                    )
                )
            }
        ) {
            Text(text = stringResource(R.string.label_send))
        }
    }
}

