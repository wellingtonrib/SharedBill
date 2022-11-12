package br.com.jwar.sharedbill.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.core.toCurrency
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import java.math.BigDecimal
import java.util.UUID

data class GroupUiModel(
    val id: String = "",
    val title: String = "",
    val membersNames: String = "",
    val members: List<UserUiModel> = emptyList(),
    val payments: List<PaymentUiModel> = emptyList(),
    val balance: Map<String, BigDecimal> = mapOf()
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
                UserUiModel.sample(),
                UserUiModel.sample(),
                UserUiModel.sample(),
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
            )
        )
    }
}
