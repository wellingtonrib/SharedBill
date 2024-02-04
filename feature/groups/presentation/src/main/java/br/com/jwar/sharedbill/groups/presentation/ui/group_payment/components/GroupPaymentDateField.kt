package br.com.jwar.sharedbill.groups.presentation.ui.group_payment.components

import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.components.Field
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.utility.extensions.format
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiError
import java.util.Calendar
import java.util.Date

@Composable
fun PaymentDateField(
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    dateTime: Long = 0L,
    error: PaymentUiError? = null,
    onValueChange: (Long) -> Unit,
) {
    Field {

        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        val calendar = remember { Calendar.getInstance().apply { time = Date(dateTime) } }

        var formattedDateValue by remember { mutableStateOf(TextFieldValue(Date(dateTime).format())) }

        val datePickerDialog = remember {
            DatePickerDialog(
                context,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    "$dayOfMonth/${month + 1}/$year".let { dataString ->
                        formattedDateValue = TextFieldValue(dataString)
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        onValueChange(calendar.timeInMillis)
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
//            datePicker.maxDate = calendar.timeInMillis
            }
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
            onValueChange = { },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction
            ),
            isError = error?.message?.asString().isNullOrBlank().not(),
            supportingText = { error?.message?.AsText(AppTheme.colors.error) },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentDateField() {
    SharedBillTheme {
        PaymentDateField() {

        }
    }
}