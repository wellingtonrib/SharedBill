package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupBalance(group: GroupUiModel) {
    if (group.balance.isEmpty()) return

    Column {
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
        Row {
            Text(text = stringResource(id = R.string.label_group_total_spent), fontWeight = FontWeight.Bold)
            HorizontalSpacerSmall()
            Text(text = group.total, fontWeight = FontWeight.Bold)
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