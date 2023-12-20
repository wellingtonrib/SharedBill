package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun TextFieldWithSuggestions(
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    label: @Composable() (() -> Unit)? = null,
    placeholder: @Composable() (() -> Unit)? = null,
    text: String,
    suggestions: List<String>,
    showSuggestions: Boolean = false,
    isError: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    supportingText: @Composable() (() -> Unit)? = null,
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text)) }
    var dropDownWidth by remember { mutableIntStateOf(0) }
    val isDropdownVisible = remember { mutableStateOf(showSuggestions) }
    val focusRequester = remember { FocusRequester() }

    Column {
        OutlinedTextField(
            modifier = modifier
                .focusRequester(focusRequester)
                .onSizeChanged { dropDownWidth = it.width },
            shape = MaterialTheme.shapes.medium,
            value = textFieldValue,
            label = label,
            placeholder = placeholder,
            onValueChange = { newValue ->
                textFieldValue = newValue.copy(text = newValue.text.take(maxLength))
                onValueChange(textFieldValue)
                isDropdownVisible.value = newValue.text.isEmpty()
            },
            isError = isError,
            supportingText = supportingText ?: {
                 if (maxLength < Int.MAX_VALUE) {
                     Text(
                         text = "${textFieldValue.text.length}/$maxLength",
                         modifier = Modifier.fillMaxWidth(),
                         textAlign = TextAlign.End,
                     )
                 }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = imeAction
            ),
        )
        DropdownMenu(
            modifier = Modifier.width(with(LocalDensity.current) { dropDownWidth.toDp() }),
            expanded = isDropdownVisible.value,
            onDismissRequest = {
                isDropdownVisible.value = false
                if (textFieldValue.text.isEmpty()) {
                    focusRequester.requestFocus()
                }
            }
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    onClick = {
                        textFieldValue = TextFieldValue(suggestion)
                            .copy(selection = TextRange(suggestion.length))
                        isDropdownVisible.value = false
                        onValueChange(textFieldValue)
                        focusRequester.requestFocus()
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
            text = "",
            suggestions = listOf(),
        )
    }
}