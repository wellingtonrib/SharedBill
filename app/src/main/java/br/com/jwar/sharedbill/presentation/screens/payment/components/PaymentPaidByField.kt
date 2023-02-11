package br.com.jwar.sharedbill.presentation.screens.payment.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.core.designsystem.components.SelectDialog
import br.com.jwar.sharedbill.presentation.screens.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun PaymentPaidByField(
    params: PaymentContract.PaymentParams,
    onPaymentParamsChange: (PaymentContract.PaymentParams) -> Unit = {},
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
        HorizontalSpacerMedium()
        Button(
            onClick = { isPaidBySelecting.value = true },
            modifier = Modifier.weight(1f),
        )
        {
            Text(text = params.paidBy.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentPaidByField() {
    SharedBillTheme {
        PaymentPaidByField(
            PaymentContract.PaymentParams.sample()
        )
    }
}