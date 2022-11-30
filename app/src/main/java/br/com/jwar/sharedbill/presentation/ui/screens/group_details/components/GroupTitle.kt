package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

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