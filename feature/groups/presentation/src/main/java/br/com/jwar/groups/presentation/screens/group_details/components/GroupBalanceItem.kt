package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.horizontalSpaceSmall
import java.math.BigDecimal

@Composable
fun GroupBalanceItem(
    entry: Map.Entry<String, BigDecimal>,
    group: GroupUiModel
) {
    Row {
        val (member, value) = entry
        Text(text = member)
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = group.getBalanceTextFromValue(value),
            color = group.getBalanceColorFromValue(value)
        )
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