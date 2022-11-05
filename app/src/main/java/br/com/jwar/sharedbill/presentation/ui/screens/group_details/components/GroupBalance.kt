package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.horizontalSpaceSmall
import org.w3c.dom.Text

@Composable
fun GroupBalance(group: GroupUiModel) {
    if (group.balance.isEmpty()) {
        Text(stringResource(R.string.message_no_expenses))
        return
    }
    Column {
        group.balance.forEach { entry ->
            Row {
                val (member, value) = entry
                Text(text = member)
                Spacer(modifier = Modifier.horizontalSpaceSmall())
                Text(
                    text = group.getBalanceTextFromValue(value),
                    color = group.getBalanceColorFromValue(value)
                )
            }
        }
    }
}