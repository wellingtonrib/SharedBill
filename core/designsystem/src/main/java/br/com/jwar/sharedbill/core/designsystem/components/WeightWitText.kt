package br.com.jwar.sharedbill.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SHOW_SIDE_BUTTONS_DELAY_IN_MILLIS = 2000L

@Composable
fun WeightWitText(
    modifier: Modifier = Modifier,
    text: String,
    weight: Int,
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    onWeightChange: (Int) -> Unit,
) {
    var currentWeight by remember { mutableIntStateOf(weight) }
    val isZeroWeight by remember { derivedStateOf { currentWeight == 0 } }
    var shouldShowSideButtons by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun showSideButtons() {
        shouldShowSideButtons = true
        scope.coroutineContext.cancelChildren()
        scope.launch {
            delay(SHOW_SIDE_BUTTONS_DELAY_IN_MILLIS)
            shouldShowSideButtons = false
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(visible = shouldShowSideButtons) {
                Icon(
                    modifier = Modifier.clickable {
                        showSideButtons()
                        currentWeight = currentWeight.inc()
                        onWeightChange(currentWeight)
                    },
                    imageVector = Icons.Outlined.KeyboardArrowUp,
                    contentDescription = stringResource(id = R.string.description_increment),
                    tint = colorScheme.primary,
                )
            }
            Button(onClick = { showSideButtons() }) {
                Text(text = "${currentWeight}x",)
            }
            AnimatedVisibility(visible = shouldShowSideButtons) {
                Icon(
                    modifier = Modifier.clickable {
                        showSideButtons()
                        currentWeight = currentWeight.dec()
                        if (currentWeight < 0) currentWeight = 0
                        onWeightChange(currentWeight)
                    },
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = stringResource(id = R.string.description_decrement),
                    tint = colorScheme.primary,
                )
            }
        }
        HorizontalSpacerSmall()
        Text(
            text = text,
            color = if (isZeroWeight) Color.Gray else LocalContentColor.current
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewPaymentPaidToField() {
    SharedBillTheme {
        WeightWitText(
            text = "Test",
            weight = 0,
        ) {}
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPaymentPaidToFieldDarkMode() {
    SharedBillTheme {
        WeightWitText(
            text = "Test",
            weight = 0,
        ) {}
    }
}
