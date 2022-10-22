package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract

@Composable
fun PaymentValueField(
    params: PaymentContract.SendPaymentParams,
    onPaymentParamsChange: (PaymentContract.SendPaymentParams) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        value = params.value,
        label = { Text(text = stringResource(id = R.string.label_payment_value)) },
        placeholder = { Text(text = stringResource(id = R.string.placeholder_payment_value)) },
        onValueChange = { onPaymentParamsChange(params.copy(value = it)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        isError = params.error is PaymentUiError.EmptyValueError
    )
}