package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupTitle(group: GroupUiModel) {
    Text(
        text = group.title.ifEmpty { stringResource(id = R.string.label_unnamed) },
        style = AppTheme.typo.titleLarge
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupTitle() {
    SharedBillTheme {
        GroupTitle(group = GroupUiModel.sample())
    }
}