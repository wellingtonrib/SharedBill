package br.com.jwar.sharedbill.core.designsystem.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium

interface Selectable {
    val selectableLabel: String
}

@Composable
fun <T: Selectable> SelectDialog(
    title: String,
    message: String,
    options: Map<T, Boolean>,
    action: String = stringResource(id = R.string.label_ok),
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
    var selection by remember { mutableStateOf(options.values.toList()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidthPaddingMedium(),
            content = {
            item {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Text(text = message)
            }
            itemsIndexed(selection) { index, selected ->
                SelectOption(options.keys.toList()[index], selected, isMultiChoice) { checked ->
                    selection = if (isMultiChoice) {
                        selection.mapIndexed { i, v -> if (i == index) checked else v }
                    } else {
                        List(selection.size) { it == index }
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
    var checkedState by remember { mutableStateOf(isSelected) }

    Row(
        modifier = Modifier.clickable {
            checkedState = checkedState.not()
            onSelectionChange(checkedState)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isMultiChoice) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checked ->
                    checkedState = checked
                    onSelectionChange(checkedState)
                }
            )
        } else {
            RadioButton(
                selected = isSelected,
                onClick = {
                    checkedState = checkedState.not()
                    onSelectionChange(checkedState)
                }
            )
        }
        Text(text = item.selectableLabel, modifier = Modifier.weight(1f))
    }
}

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreviewSelectDialogContent() {
    SharedBillTheme {
        Scaffold {
            SelectDialogContent(
                title = "Title",
                message = "Select a user",
                options = mapOf(
                    object: Selectable {
                        override val selectableLabel: String
                            get() = "Test"
                    } to true,
                ),
                action = "Ok",
                isMultiChoice = true
            ) {

            }
        }
    }
}