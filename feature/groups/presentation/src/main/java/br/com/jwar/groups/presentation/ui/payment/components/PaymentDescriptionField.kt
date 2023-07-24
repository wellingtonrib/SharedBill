package br.com.jwar.groups.presentation.ui.payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.components.TextFieldWithSuggestions
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun PaymentDescriptionField(
    params: PaymentContract.PaymentParams,
    onPaymentParamsChange: (PaymentContract.PaymentParams) -> Unit = {}
) {
    val suggestions = stringArrayResource(R.array.samples_payment_description)
    val isError = params.error is PaymentUiError.EmptyDescriptionError

    TextFieldWithSuggestions(
        modifier = Modifier.fillMaxWidth(),
        value = params.description,
        label = { Text(text = stringResource(R.string.label_payment_description)) },
        placeholder = { Text(text = stringResource(R.string.placeholder_payment_description)) },
        suggestions = suggestions.toList(),
        onValueChange = { newValue ->
            onPaymentParamsChange(params.copy(description = newValue.text))
        },
        isError = isError,
        supportingText = { if (isError) params.error?.message?.asText() },
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