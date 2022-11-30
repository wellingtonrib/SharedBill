package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun PaymentDescriptionField(
    params: PaymentContract.PaymentParams,
    onPaymentParamsChange: (PaymentContract.PaymentParams) -> Unit = {}
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        value = params.description,
        label = { Text(text = stringResource(R.string.label_payment_description)) },
        placeholder = { Text(text = stringResource(R.string.placeholder_payment_description)) },
        onValueChange = { onPaymentParamsChange(params.copy(description = it)) },
        isError = params.error is PaymentUiError.EmptyDescriptionError
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_() {
    SharedBillTheme {
        PaymentDescriptionField(
            PaymentContract.PaymentParams.sample()
        )
    }
}