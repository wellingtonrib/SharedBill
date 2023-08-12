package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CheckboxWitText(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    var checkedState by remember { mutableStateOf(isChecked) }

    Row(
        modifier = modifier
            .clickable {
                val newValue = !checkedState
                checkedState = newValue
                onCheckedChange(newValue)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier,
            checked = checkedState,
            onCheckedChange = { newValue ->
                checkedState = newValue
                onCheckedChange(newValue)
            }
        )
        Text(
            text = text,
            color = if (checkedState) LocalContentColor.current else Color.Gray
        )
    }
}