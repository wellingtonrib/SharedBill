package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingSmall

@Composable
fun Pill(
    text: String,
    textColor: Color,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {
        Text(
            modifier = Modifier.paddingSmall(),
            text = text,
            style = AppTheme.typo.titleSmall,
            color = textColor
        )
    }
}