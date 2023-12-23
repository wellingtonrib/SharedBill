package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun WeightWitText(
    modifier: Modifier = Modifier,
    text: String,
    weight: Int,
    onWeightChange: (Int) -> Unit,
) {
    var currentWeight by remember { mutableIntStateOf(weight) }
    val isValidWeight by remember { derivedStateOf { currentWeight > 0 } }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Icon(
                modifier = Modifier.clickable {
                    currentWeight = currentWeight.inc()
                    onWeightChange(currentWeight)
                },
                imageVector = Icons.Outlined.KeyboardArrowUp,
                contentDescription = stringResource(id = R.string.description_increment)
            )
            Text(
                text = "${currentWeight}x",
                color = if (isValidWeight) LocalContentColor.current else Color.Gray
            )
            Icon(
                modifier = Modifier.clickable {
                    currentWeight = currentWeight.dec(); if (currentWeight < 0) currentWeight = 0
                    onWeightChange(currentWeight)
                },
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = stringResource(id = R.string.description_decrement)
            )
        }
        Text(
            text = text,
            color = if (isValidWeight) LocalContentColor.current else Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentPaidToField() {
    SharedBillTheme {
        WeightWitText(
            text = "Test",
            weight = 0,
        ) {

        }
    }
}