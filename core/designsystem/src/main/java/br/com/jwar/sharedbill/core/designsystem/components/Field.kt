package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Field(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    content()
}