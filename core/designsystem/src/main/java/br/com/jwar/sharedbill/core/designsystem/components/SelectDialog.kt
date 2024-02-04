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
    val selectableId: String
}

@Composable
fun <T: Selectable> SelectDialog(
    title: String,
    message: String,
    options: Map<T, Boolean>,
    defaultSelection: List<T> = options.keys.toList(),
    action: String = stringResource(id = R.string.label_send),
    isMultiChoice: Boolean = true,
    needConfirm: Boolean = true,
    onDismiss: () -> Unit,
    onSelect: (List<T>) -> Unit
) {
    val optionsKeys by remember { derivedStateOf { options.keys.toList() }}
    var selection by remember { mutableStateOf(defaultSelection.toMutableList()) }

    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
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
                    itemsIndexed(optionsKeys) { index, item ->
                        SelectOption(optionsKeys[index], selection.any { it.selectableId == item.selectableId }, isMultiChoice) { checked ->
                            selection = if (isMultiChoice) {
                                selection.apply {
                                    if (checked) {
                                        add(item)
                                    } else {
                                        remove(item)
                                    }
                                }
                            } else {
                                mutableListOf(item)
                            }
                            if (!needConfirm) {
                                onSelect(selection)
                            }
                        }
                    }
                    if (needConfirm) {
                        item {
                            Button(
                                onClick = {
                                    onSelect(selection)
                                }
                            ) {
                                Text(text = action)
                            }
                        }
                    }
                }
            )
        }
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
            SelectDialog(
                title = "Title",
                message = "Select a user",
                options = mapOf(
                    object: Selectable {
                        override val selectableLabel: String
                            get() = "Test"
                        override val selectableId: String
                            get() = "1"
                    } to true,
                ),
                action = "Ok",
                isMultiChoice = true,
                onDismiss = {},
            ) {

            }
        }
    }
}