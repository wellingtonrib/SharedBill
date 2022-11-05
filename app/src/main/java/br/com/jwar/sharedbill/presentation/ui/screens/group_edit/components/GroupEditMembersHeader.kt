package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun GroupEditMembersHeader() {
    Text(
        style = MaterialTheme.typography.titleLarge,
        text = stringResource(R.string.label_group_members)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupTitle() {
    SharedBillTheme {
        GroupEditMembersHeader()
    }
}

