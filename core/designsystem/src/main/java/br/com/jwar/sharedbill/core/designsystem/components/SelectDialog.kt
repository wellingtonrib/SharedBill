package br.com.jwar.sharedbill.core.designsystem.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium

@Composable
@Suppress("LongParameterList")
fun <T : Selectable> SelectDialog(
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
    val optionsKeys by remember { derivedStateOf { options.keys.toList() } }
    var selection by remember { mutableStateOf(defaultSelection) }

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
                        SelectOption(
                            item = optionsKeys[index],
                            isSelected = selection.any { it.selectableId == item.selectableId },
                            isMultiChoice = isMultiChoice
                        ) { checked ->
                            selection = if (isMultiChoice) {
                                selection.toMutableList().apply {
                                    if (checked) {
                                        add(item)
                                    } else {
                                        remove(item)
                                    }
                                }.toList()
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

interface Selectable {
    val selectableLabel: String
    val selectableId: String
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
                    object : Selectable {
                        override val selectableLabel: String
                            get() = "Test"
                        override val selectableId: String
                            get() = "1"
                    } to true,
                ),
                action = "Ok",
                isMultiChoice = true,
                onDismiss = {},
            ) {}
        }
    }
}
