package br.com.jwar.sharedbill.presentation.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.core.orFalse
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

interface Selectable {
    fun getLabel(): String
}

@Composable
fun MultipleChoiceDialog(
    title: String,
    message: String,
    options: Map<Selectable, Boolean>,
    action: String = "Ok",
    onDismiss: () -> Unit,
    onAction: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        MultipleChoiceDialogContent(
            title = title,
            message = message,
            options = options,
            action = action,
            onAction = onAction
        )
    }
}

@Composable
private fun MultipleChoiceDialogContent(
    title: String,
    message: String,
    options: Map<Selectable, Boolean>,
    action: String,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            content = {
            item {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Text(text = message)
            }
            items(options.keys.toList()) { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = item.getLabel(), modifier = Modifier.weight(1f))
                    Checkbox(
                        checked = options[item].orFalse(),
                        onCheckedChange = { }
                    )
                }
            }
            item {
                Button(
                    onClick = { onAction() }
                ) {
                    Text(text = action)
                }
            }
        })
    }
}

@Preview
@Composable
fun PreviewMultipleChoiceDialogContent() {
    SharedBillTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            MultipleChoiceDialogContent(
                title = "Title",
                message = "Select a user",
                options = mapOf(
                    User(name = "User One") to true,
                    User(name = "User Two") to false,
                    User(name = "User Three") to false,
                ),
                action = "Ok"
            ) {

            }
        }
    }
}