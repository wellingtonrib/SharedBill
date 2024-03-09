package br.com.jwar.sharedbill.groups.presentation.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.components.Pill
import br.com.jwar.sharedbill.core.designsystem.components.UserAvatar
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import java.math.BigDecimal

private const val SETTLED_UP_THRESHOLD = 0.05

@Composable
fun GroupMemberBalanceCard(
    entry: Map.Entry<GroupMemberUiModel, BigDecimal>,
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
                modifier = Modifier.weight(1f),
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
            GroupBalanceValue(value = value)
        }
    }
}

@Composable
private fun GroupBalanceValue(
    modifier: Modifier = Modifier,
    value: BigDecimal
) {
    when {
        value.abs() < BigDecimal.valueOf(SETTLED_UP_THRESHOLD) ->
            Pill(
                modifier = modifier,
                text = stringResource(R.string.message_settled_up),
                textColor = AppTheme.colors.onSurface,
                backgroundColor = AppTheme.colors.surface
            )
        value.signum() < 0 ->
            Pill(
                modifier = modifier,
                text = "+${value.abs().toCurrency()}",
                textColor = AppTheme.colors.primary,
                backgroundColor = AppTheme.colors.primaryContainer
            )
        value.signum() > 0 ->
            Pill(
                modifier = modifier,
                text = "-${value.abs().toCurrency()}",
                textColor = AppTheme.colors.error,
                backgroundColor = AppTheme.colors.errorContainer
            )
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("MagicNumber")
fun PreviewGroupBalanceItem() {
    SharedBillTheme {
        GroupMemberBalanceCard(
            entry = hashMapOf(
                GroupMemberUiModel.sample()
                    .copy(name = "José Wellington Alves Ribeiro") to BigDecimal.valueOf(
                    SETTLED_UP_THRESHOLD
                )
            ).entries.first(),
        )
    }
}
