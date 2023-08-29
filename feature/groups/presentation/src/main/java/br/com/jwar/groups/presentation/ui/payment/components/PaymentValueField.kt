package br.com.jwar.groups.presentation.ui.payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun PaymentValueField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = FocusRequester(),
    imeAction: ImeAction = ImeAction.Next,
    value: String = "",
    error: PaymentUiError? = null,
    onValueChange: (TextFieldValue) -> Unit,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }

    OutlinedTextField(
        modifier = modifier
            .focusRequester(focusRequester ?: FocusRequester())
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        value = textFieldValue,
        label = { Text(text = stringResource(id = R.string.label_payment_value)) },
        placeholder = { Text(text = stringResource(id = R.string.placeholder_payment_value)) },
        onValueChange = { newValue ->
            textFieldValue = newValue
            onValueChange(newValue)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = imeAction
        ),
        isError = error?.message?.asString().isNullOrBlank().not(),
        supportingText = { error?.message?.AsText(AppTheme.colors.error) },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentValueField() {
    SharedBillTheme {
        PaymentValueField() {

        }
    }
}