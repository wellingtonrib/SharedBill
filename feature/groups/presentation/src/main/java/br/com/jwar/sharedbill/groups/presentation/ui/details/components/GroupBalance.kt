package br.com.jwar.sharedbill.groups.presentation.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.testing.TestTags

@Composable
fun GroupBalance(
    modifier: Modifier = Modifier,
    group: GroupUiModel,
) {
    if (group.balance.isEmpty()) return

    LazyColumn(
        modifier = modifier.testTag(TestTags.BalanceList),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4),
    ) {
        items(group.balance.entries.toList()) { entry ->
            GroupMemberBalanceCard(entry)
        }
        item {
            Text(
                modifier = Modifier.padding(vertical = AppTheme.dimens.space8),
                text = stringResource(R.string.label_total_spent, group.total),
                style = AppTheme.typo.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupBalance() {
    SharedBillTheme {
        GroupBalance(group = GroupUiModel.sample())
    }
}
