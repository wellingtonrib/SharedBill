package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.components.Title
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupBalance(group: GroupUiModel) {
    if (group.balance.isEmpty()) return

    Column {
        Title(stringResource(R.string.label_group_balance))
        VerticalSpacerSmall()
        LazyColumn {
            items(group.balance.entries.toList()) { entry ->
                GroupBalanceItem(entry, group)
            }
        }
        VerticalSpacerSmall()
        Text(
            text = stringResource(R.string.label_total_spent, group.total),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupBalance() {
    SharedBillTheme {
        GroupBalance(group = GroupUiModel.sample())
    }
}