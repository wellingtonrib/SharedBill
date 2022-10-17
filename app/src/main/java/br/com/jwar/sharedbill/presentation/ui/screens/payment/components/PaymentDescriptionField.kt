package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R

@Composable
fun PaymentDescriptionField(description: MutableState<String>) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        value = description.value,
        label = { Text(text = stringResource(R.string.label_payment_description)) },
        placeholder = { Text(text = stringResource(R.string.placeholder_payment_description)) },
        onValueChange = { description.value = it }
    )
}