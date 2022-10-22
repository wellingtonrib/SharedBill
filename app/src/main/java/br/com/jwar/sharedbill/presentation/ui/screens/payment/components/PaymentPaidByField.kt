package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.generic_components.SelectDialog
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import br.com.jwar.sharedbill.presentation.ui.theme.horizontalSpaceMedium

@Composable
fun PaymentPaidByField(
    params: PaymentContract.SendPaymentParams,
    onPaymentParamsChange: (PaymentContract.SendPaymentParams) -> Unit,
) {
    val isPaidBySelecting = remember { mutableStateOf(false) }
    if (isPaidBySelecting.value) {
        SelectDialog(
            title = stringResource(id = R.string.label_payment_paid_by),
            message = stringResource(id = R.string.placeholder_payment_paid_by),
            options = params.group.members.associateWith { it.uid == params.paidBy.uid },
            isMultiChoice = false,
            onDismiss = {
                isPaidBySelecting.value = false
            },
            onSelect = {
                isPaidBySelecting.value = false
                onPaymentParamsChange(params.copy(paidBy = it.first()))
            }
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = stringResource(R.string.label_payment_paid_by))
        Spacer(modifier = Modifier.horizontalSpaceMedium())
        OutlinedButton(
            onClick = { isPaidBySelecting.value = true },
            modifier = Modifier.weight(1f)
        )
        {
            Text(text = params.paidBy.name)
        }
    }
}