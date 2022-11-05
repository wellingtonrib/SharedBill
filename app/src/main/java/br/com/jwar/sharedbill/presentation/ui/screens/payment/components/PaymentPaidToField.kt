package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.presentation.ui.generic_components.SelectDialog
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.horizontalSpaceMedium

@Composable
fun PaymentPaidToField(
    params: PaymentContract.SendPaymentParams,
    onPaymentParamsChange: (PaymentContract.SendPaymentParams) -> Unit = {}
) {
    val isPaidToSelecting = remember { mutableStateOf(false) }
    if (isPaidToSelecting.value) {
        SelectDialog(
            title = stringResource(R.string.label_payment_paid_to),
            message = stringResource(R.string.placeholder_payment_paid_to),
            options = params.group.members.associateWith { params.paidTo.contains(it) },
            onDismiss = {
                isPaidToSelecting.value = false
            },
            onSelect = {
                isPaidToSelecting.value = false
                onPaymentParamsChange(params.copy(paidTo = it))
            }
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = stringResource(R.string.label_payment_paid_to))
        Spacer(modifier = Modifier.horizontalSpaceMedium())
        Button(
            onClick = { isPaidToSelecting.value = true },
            modifier = Modifier.weight(1f),
            border = if (params.error is PaymentUiError.EmptyRelatedMembersError)
                        BorderStroke(width = 1.0.dp, color = AppTheme.colors.error)
                    else null
            )
        {
            Text(text = getPaidToText(params = params))
        }
    }
}

@Composable
private fun getPaidToText(params: PaymentContract.SendPaymentParams) =
    if (params.paidTo.size == params.group.members.size) {
        stringResource(id = R.string.message_paid_to_all)
    } else {
        params.paidTo.joinToString { it.name }
    }

@Preview(showBackground = true)
@Composable
fun PreviewPaymentPaidToField() {
    SharedBillTheme {
        PaymentPaidToField(
            PaymentContract.SendPaymentParams.sample()
        )
    }
}