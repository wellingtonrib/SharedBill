package br.com.jwar.groups.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.groups.presentation.R
import java.math.BigDecimal
import java.util.*

data class GroupUiModel(
    val id: String = "",
    val title: String = "",
    val membersNames: String = "",
    val members: List<GroupMemberUiModel> = emptyList(),
    val payments: List<PaymentUiModel> = emptyList(),
    val balance: Map<String, BigDecimal> = mapOf(),
    val total: String = "",
    val isCurrentUserOwner: Boolean = false
) {
    @Composable
    fun getBalanceTextFromValue(value: BigDecimal) = when {
        value > BigDecimal.ZERO -> stringResource(
            R.string.message_owes,
            value.toCurrency()
        )
        value < BigDecimal.ZERO -> stringResource(
            R.string.message_is_owned,
            value.abs().toCurrency()
        )
        else -> stringResource(R.string.message_settled_up)
    }

    @Composable
    fun getBalanceColorFromValue(value: BigDecimal) =
        if (value > BigDecimal.ZERO) AppTheme.colors.error
        else AppTheme.colors.primary

    companion object {
        fun sample() = GroupUiModel(
            id = UUID.randomUUID().toString(),
            title = "Group Sample",
            membersNames = "Member One, Member Two, Member Three",
            members = listOf(
                GroupMemberUiModel.sample(),
                GroupMemberUiModel.sample(),
                GroupMemberUiModel.sample(),
            ),
            payments = listOf(
                PaymentUiModel.sample(),
                PaymentUiModel.sample(),
                PaymentUiModel.sample(),
            ),
            balance = mapOf(
                "Member One" to BigDecimal("100"),
                "Member Two" to BigDecimal("-100"),
                "Member Three" to BigDecimal.ZERO,
            ),
            total = "$300"
        )
    }
}

