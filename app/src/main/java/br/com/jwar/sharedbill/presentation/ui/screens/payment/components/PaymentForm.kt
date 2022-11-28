package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.VerticalSpacerMedium

@Composable
fun PaymentForm(
    params: PaymentContract.SendPaymentParams,
    onPaymentParamsChange: (PaymentContract.SendPaymentParams) -> Unit = {},
) {
    Column {
        PaymentDescriptionField(params, onPaymentParamsChange)
        VerticalSpacerMedium()
        PaymentValueField(params, onPaymentParamsChange)
        VerticalSpacerMedium()
        PaymentDateField(params, onPaymentParamsChange)
        VerticalSpacerMedium()
        PaymentPaidByField(params, onPaymentParamsChange)
        VerticalSpacerMedium()
        PaymentPaidToField(params, onPaymentParamsChange)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentForm() {
    SharedBillTheme {
        PaymentForm(
            PaymentContract.SendPaymentParams.sample()
        )
    }
}

