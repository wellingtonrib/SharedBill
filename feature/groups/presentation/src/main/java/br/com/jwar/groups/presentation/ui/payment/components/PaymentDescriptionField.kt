package br.com.jwar.groups.presentation.ui.payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.core.designsystem.components.TextFieldWithSuggestions
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun PaymentDescriptionField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    description: String = "",
    error: PaymentUiError.InvalidDescriptionError? = null,
    onValueChange: (TextFieldValue) -> Unit = {},
) {
    val suggestions = stringArrayResource(R.array.samples_payment_description)

    TextFieldWithSuggestions(
        modifier = modifier.fillMaxWidth(),
        focusRequester = focusRequester,
        label = { Text(text = stringResource(R.string.label_payment_description)) },
        placeholder = { Text(text = stringResource(R.string.placeholder_payment_description)) },
        text = description,
        suggestions = suggestions.toList(),
        isError = error?.message?.asString().isNullOrBlank().not(),
        supportingText = { error?.message?.AsText(AppTheme.colors.error) },
        onValueChange = onValueChange,
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_() {
    SharedBillTheme {
        PaymentDescriptionField()
    }
}