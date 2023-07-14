package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium

@Composable
fun InputDialog(
    label: String = "",
    placeholder: String = "",
    action: String = stringResource(id = R.string.label_save),
    onDismiss: () -> Unit,
    minLength: Int = 1,
    minWords: Int = 1,
    onAction: (string: String) -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
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
                    onValueChange = { input = it },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
                VerticalSpacerSmall()
                Button(
                    onClick = { onAction(input.trim()) },
                    enabled = input.length > minLength && input.trim().split("\\s+".toRegex()).size >= minWords
                ) {
                    Text(text = action)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputDialogContent() {
    SharedBillTheme {
        InputDialog(
            label = "Label",
            placeholder = "Placeholder",
            action = "Action",
            onDismiss = {},
            onAction = {},
        )
    }
}