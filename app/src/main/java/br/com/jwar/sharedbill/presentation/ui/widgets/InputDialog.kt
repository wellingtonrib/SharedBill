package br.com.jwar.sharedbill.presentation.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun InputDialog(
    label: String = "",
    placeholder: String = "",
    action: String = "Save",
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
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                value = input,
                label = { Text(text = label) },
                placeholder = { Text(text = placeholder) },
                onValueChange = { input = it }
            )
            Button(
                onClick = { onAction(input) }
            ) {
                Text(text = action)
            }
        }
    }
}

@Preview
@Composable
fun PreviewGroupMemberAddContent() {
    SharedBillTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            InputDialogContent(
                label = "Label",
                placeholder = "Placeholder",
                action = "Action"
            ) {

            }
        }
    }
}