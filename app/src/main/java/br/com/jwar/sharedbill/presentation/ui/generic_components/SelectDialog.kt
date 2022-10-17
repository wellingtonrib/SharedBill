package br.com.jwar.sharedbill.presentation.ui.generic_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium

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
    var selection by remember { mutableStateOf(options.values.toMutableList()) }

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
        Scaffold {
            SelectDialogContent(
                title = "Title",
                message = "Select a user",
                options = mapOf(
                    UserUiModel.sample() to true,
                    UserUiModel.sample() to true,
                    UserUiModel.sample() to false,
                ),
                action = "Ok",
                isMultiChoice = true
            ) {

            }
        }
    }
}