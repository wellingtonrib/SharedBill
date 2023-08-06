package br.com.jwar.groups.presentation.ui.group_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.components.UserAvatar
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.core.designsystem.theme.paddingSmall
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.presentation.R
import java.math.BigDecimal

@Composable
fun GroupBalanceItem(
    entry: Map.Entry<GroupMemberUiModel, BigDecimal>,
    group: GroupUiModel
) {
    val (member, value) = entry
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidthPaddingMedium(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UserAvatar(
                    user = member.toUserUiModel(),
                    avatarSize = 32.dp,
                )
                HorizontalSpacerSmall()
                Text(
                    text = member.name,
                    style = AppTheme.typo.titleMedium
                )
            }
            HorizontalSpacerMedium()
            GroupBalanceValue(value)
        }
    }
}

@Composable
private fun GroupBalanceValue(
    value: BigDecimal
) {
    when {
        value > BigDecimal.ZERO ->
            Pill(
                text = "+ ${value.toCurrency()}",
                textColor = AppTheme.colors.primary,
                backgroundColor = AppTheme.colors.primaryContainer
            )
        value < BigDecimal.ZERO ->
            Pill(
                text = value.toCurrency(),
                textColor = AppTheme.colors.error,
                backgroundColor = AppTheme.colors.onError
            )
        else ->
            Pill(
                text = stringResource(R.string.message_settled_up),
                textColor = AppTheme.colors.surface,
                backgroundColor = AppTheme.colors.onSurface
            )
    }
}

@Composable
private fun Pill(
    text: String,
    textColor: Color,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {
        Text(
            modifier = Modifier.paddingSmall(),
            text = text,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupBalanceItem() {
    SharedBillTheme {
        GroupBalanceItem(
            entry = hashMapOf(GroupMemberUiModel.sample() to BigDecimal.ONE).entries.first(),
            group = GroupUiModel.sample()
        )
    }
}