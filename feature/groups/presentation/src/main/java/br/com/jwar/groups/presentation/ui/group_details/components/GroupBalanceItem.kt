package br.com.jwar.groups.presentation.ui.group_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import java.math.BigDecimal

@Composable
fun GroupBalanceItem(
    entry: Map.Entry<String, BigDecimal>,
    group: GroupUiModel
) {
    val (member, value) = entry
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidthPaddingMedium(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = member)
            HorizontalSpacerMedium()
            Text(
                text = group.getBalanceTextFromValue(value),
                color = group.getBalanceColorFromValue(value)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupBalanceItem() {
    SharedBillTheme {
        GroupBalanceItem(
            entry = hashMapOf("User" to BigDecimal.ONE).entries.first(),
            group = GroupUiModel.sample()
        )
    }
}