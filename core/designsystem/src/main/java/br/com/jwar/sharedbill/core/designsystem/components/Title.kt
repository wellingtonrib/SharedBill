package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme

@Composable
fun Title(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(title, modifier = modifier, style = AppTheme.typo.titleLarge)
}
