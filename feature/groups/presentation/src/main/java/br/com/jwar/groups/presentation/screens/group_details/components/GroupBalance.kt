package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupBalance(group: GroupUiModel) {
    if (group.balance.isEmpty()) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.secondaryContainer,
            contentColor = AppTheme.colors.onSecondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.dimens.space_8)
        ) {
            Text(text = stringResource(R.string.label_group_balance), style = AppTheme.typo.titleLarge)
            VerticalSpacerSmall()
            group.balance.forEach { entry ->
                Row {
                    val (member, value) = entry
                    Text(text = member)
                    HorizontalSpacerSmall()
                    Text(
                        text = group.getBalanceTextFromValue(value),
                        color = group.getBalanceColorFromValue(value)
                    )
                }
            }
            VerticalSpacerSmall()
            Text(
                text = stringResource(R.string.label_total_spent, group.total),
                fontWeight = FontWeight.Bold
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