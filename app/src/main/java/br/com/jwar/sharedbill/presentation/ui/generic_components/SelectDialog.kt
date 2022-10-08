package br.com.jwar.sharedbill.presentation.ui.generic_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

interface Selectable {
    val selectableLabel: String
}

@Composable
fun <T: Selectable> SelectDialog(
    title: String,
    message: String,
    options: Map<T, Boolean>,
    action: String = "Ok",
    isMultiChoice: Boolean = true,
    onDismiss: () -> Unit,
    onSelect: (List<T>) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        SelectDialogContent(
            title = title,
            message = message,
            options = options,
            action = action,
            isMultiChoice = isMultiChoice,
            onSelect = onSelect
        )
    }
}

@Composable
private fun <T: Selectable> SelectDialogContent(
    title: String,
    message: String,
    options: Map<T, Boolean>,
    action: String,
    isMultiChoice: Boolean,
    onSelect: (List<T>) -> Unit
) {
    var selection by remember { mutableStateOf(options.values.toMutableList()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = {
            item {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Text(text = message)
            }
            itemsIndexed(selection) { index, selected ->
                SelectOption(options.keys.toList()[index], selected, isMultiChoice) { checked ->
                    if (isMultiChoice) {
                        selection[index] = checked
                    } else {
                        selection = List(selection.size) { it == index }.toMutableList()
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        onSelect(options.keys.filterIndexed { index, _ -> selection[index] }.map { it })
                    }
                ) {
                    Text(text = action)
                }
            }
        })
    }
}

@Composable
private fun <T : Selectable> SelectOption(
    item: T,
    isSelected: Boolean,
    isMultiChoice: Boolean,
    onSelectionChange: (Boolean) -> Unit
) {
    val checkedState = remember { mutableStateOf(isSelected) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.selectableLabel, modifier = Modifier.weight(1f))
        if (isMultiChoice) {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checked ->
                    checkedState.value = checked
                    onSelectionChange(checkedState.value)
                }
            )
        } else {
            RadioButton(
                selected = isSelected,
                onClick = {
                    checkedState.value = checkedState.value.not()
                    onSelectionChange(checkedState.value)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewSelectDialogContent() {
    SharedBillTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            SelectDialogContent(
                title = "Title",
                message = "Select a user",
                options = mapOf(
                    User(name = "User One") to true,
                    User(name = "User Two") to false,
                    User(name = "User Three") to false,
                ),
                action = "Ok",
                isMultiChoice = true
            ) {

            }
        }
    }
}