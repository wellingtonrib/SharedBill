package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.verticalSpaceMedium

@Composable
fun PaymentForm(
    params: PaymentContract.SendPaymentParams,
    onPaymentParamsChange: (PaymentContract.SendPaymentParams) -> Unit = {},
) {
    Column {
        PaymentDescriptionField(params, onPaymentParamsChange)
        Spacer(modifier = Modifier.verticalSpaceMedium())
        PaymentValueField(params, onPaymentParamsChange)
        Spacer(modifier = Modifier.verticalSpaceMedium())
        PaymentDateField(params, onPaymentParamsChange)
        Spacer(modifier = Modifier.verticalSpaceMedium())
        PaymentPaidByField(params, onPaymentParamsChange)
        Spacer(modifier = Modifier.verticalSpaceMedium())
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

