package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun GroupTitle(group: GroupUiModel) {
    Text(text = group.title, style = AppTheme.typo.titleLarge)
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupTitle() {
    SharedBillTheme {
        GroupTitle(group = GroupUiModel.sample())
    }
}