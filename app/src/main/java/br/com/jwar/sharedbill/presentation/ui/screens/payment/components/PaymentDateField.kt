package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.core.format
import br.com.jwar.sharedbill.core.parse
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import java.util.Calendar

@Composable
fun PaymentDateField(
    params: PaymentContract.SendPaymentParams,
    onPaymentParamsChange: (PaymentContract.SendPaymentParams) -> Unit
) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onPaymentParamsChange(
                params.copy(date = "$dayOfMonth/${month + 1}/$year".parse())
            )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.hasFocus) {
                    datePickerDialog.show()
                    focusManager.clearFocus()
                }
            },
        shape = MaterialTheme.shapes.medium,
        value = params.date.format(),
        label = { Text(text = stringResource(R.string.label_date)) },
        placeholder = { Text(text = stringResource(R.string.placeholder_payment_date)) },
        onValueChange = {},
        isError = params.error is PaymentUiError.EmptyDateError
    )
}