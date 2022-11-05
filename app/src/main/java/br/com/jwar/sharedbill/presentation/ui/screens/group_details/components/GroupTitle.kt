package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import org.w3c.dom.Text

@Composable
fun GroupTitle(group: GroupUiModel) {
    Text(text = group.title, style = AppTheme.typo.titleLarge)
}