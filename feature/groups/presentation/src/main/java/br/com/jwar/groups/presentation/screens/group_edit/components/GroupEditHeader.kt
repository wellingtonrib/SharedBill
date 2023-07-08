package br.com.jwar.groups.presentation.screens.group_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupEditHeader(
    group: GroupUiModel,
    onGroupUpdated: (GroupUiModel) -> Unit = {},
) {
    val titleValue = remember { mutableStateOf(TextFieldValue(group.title)) }
    val titleFocusRequester = remember { FocusRequester() }
    val isDropdownVisible = remember { mutableStateOf(titleValue.value.text.isEmpty()) }
    val suggestions = stringArrayResource(R.array.samples_group_title)

    LaunchedEffect(Unit) {
        if (titleValue.value.text.isEmpty()) {
            titleFocusRequester.requestFocus()
        }
    }

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(titleFocusRequester),
            shape = MaterialTheme.shapes.medium,
            value = titleValue.value,
            label = { Text(stringResource(R.string.label_group_title)) },
            placeholder = { Text(stringResource(R.string.placeholder_group_title)) },
            onValueChange = { newValue ->
                titleValue.value = newValue
                isDropdownVisible.value = newValue.text.isEmpty()
                onGroupUpdated(group.copy(title = newValue.text))
            },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(0.9f),
            offset = DpOffset(x = 0.dp, y = 10.dp),
            expanded = isDropdownVisible.value,
            onDismissRequest = { isDropdownVisible.value = false }
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    onClick = {
                        titleValue.value = TextFieldValue(suggestion)
                            .copy(selection = TextRange(suggestion.length))
                        isDropdownVisible.value = false
                        onGroupUpdated(group.copy(title = suggestion))
                    },
                    text = { Text(suggestion) }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewGroupEditHeader() {
    SharedBillTheme {
        GroupEditHeader(group = GroupUiModel.sample())
    }
}