package br.com.jwar.sharedbill.presentation.ui.generic_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.paddingMedium

@Composable
fun InputDialog(
    label: String = "",
    placeholder: String = "",
    action: String = stringResource(id = R.string.label_save),
    onDismiss: () -> Unit,
    onAction: (string: String) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        InputDialogContent(
            label = label,
            placeholder = placeholder,
            action = action
        ) {
            onAction(it)
        }
    }
}

@Composable
private fun InputDialogContent(
    label: String,
    placeholder: String,
    action: String,
    onAction: (input: String) -> Unit
) {
    var input by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.paddingMedium()
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                value = input,
                label = { Text(text = label) },
                placeholder = { Text(text = placeholder) },
                onValueChange = { input = it }
            )
            Button(
                onClick = { onAction(input) },
                enabled = input.isNotBlank()
            ) {
                Text(text = action)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputDialogContent() {
    SharedBillTheme {
        InputDialogContent(
            label = "Label",
            placeholder = "Placeholder",
            action = "Action"
        ) {

        }
    }
}