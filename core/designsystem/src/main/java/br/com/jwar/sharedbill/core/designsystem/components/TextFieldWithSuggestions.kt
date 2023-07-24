package br.com.jwar.sharedbill.core.designsystem.components

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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun TextFieldWithSuggestions(
    modifier: Modifier = Modifier,
    label: @Composable() (() -> Unit)? = null,
    placeholder: @Composable() (() -> Unit)? = null,
    value: String,
    suggestions: List<String>,
    isError: Boolean = false,
    supportingText: @Composable() (() -> Unit)? = null,
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    val fieldValue = remember { mutableStateOf(TextFieldValue(value)) }
    val focusRequester = remember { FocusRequester() }
    val isDropdownVisible = remember { mutableStateOf(fieldValue.value.text.isEmpty()) }

    LaunchedEffect(Unit) {
        if (fieldValue.value.text.isEmpty()) {
            focusRequester.requestFocus()
        }
    }

    Column {
        OutlinedTextField(
            modifier = modifier.focusRequester(focusRequester),
            shape = MaterialTheme.shapes.medium,
            value = fieldValue.value,
            label = label,
            placeholder = placeholder,
            onValueChange = { newValue ->
                fieldValue.value = newValue
                isDropdownVisible.value = newValue.text.isEmpty()
                onValueChange(newValue)
            },
            isError = isError,
            supportingText = supportingText,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences)
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
                        fieldValue.value = TextFieldValue(suggestion)
                            .copy(selection = TextRange(suggestion.length))
                        isDropdownVisible.value = false
                        onValueChange(fieldValue.value)
                    },
                    text = { Text(suggestion) }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTextFieldWithSuggestions() {
    SharedBillTheme {
        TextFieldWithSuggestions(
            value = "",
            suggestions = listOf()
        )
    }
}