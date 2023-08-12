package br.com.jwar.groups.presentation.ui.payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun PaymentValueField(
    modifier: Modifier = Modifier,
    value: String = "",
    error: PaymentUiError.InvalidValueError? = null,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        value = value,
        label = { Text(text = stringResource(id = R.string.label_payment_value)) },
        placeholder = { Text(text = stringResource(id = R.string.placeholder_payment_value)) },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        isError = error?.message?.asString().isNullOrBlank().not(),
        supportingText = { error?.message?.AsText(AppTheme.colors.error) },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentValueField() {
    SharedBillTheme {
        PaymentValueField {

        }
    }
}