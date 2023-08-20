package br.com.jwar.groups.presentation.ui.payment.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.utility.extensions.format
import br.com.jwar.sharedbill.core.utility.extensions.parse
import java.util.Calendar
import java.util.Date

@Composable
fun PaymentDateField(
    modifier: Modifier = Modifier,
    date: String = Date().format(),
    error: PaymentUiError.InvalidDateError? = null,
    onValueChange: (String) -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var formattedDateValue by remember { mutableStateOf(TextFieldValue(date.format())) }

    val datePickerDialog = remember {
        val calendar = Calendar.getInstance().apply { time = date.parse() }
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                "$dayOfMonth/${month + 1}/$year".let { dataString ->
                    formattedDateValue = TextFieldValue(dataString)
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.hasFocus) {
                    datePickerDialog.show()
                    focusManager.clearFocus()
                }
            },
        shape = MaterialTheme.shapes.medium,
        value = formattedDateValue,
        label = { Text(text = stringResource(R.string.label_date)) },
        placeholder = { Text(text = stringResource(R.string.placeholder_payment_date)) },
        onValueChange = { onValueChange(it.text) },
        isError = error?.message?.asString().isNullOrBlank().not(),
        supportingText = { error?.message?.AsText(AppTheme.colors.error) },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentDateField() {
    SharedBillTheme {
        PaymentDateField {

        }
    }
}