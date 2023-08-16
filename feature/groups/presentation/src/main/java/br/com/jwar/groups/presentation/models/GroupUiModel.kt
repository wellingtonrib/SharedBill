package br.com.jwar.groups.presentation.models

import java.math.BigDecimal
import java.util.UUID

data class GroupUiModel(
    val id: String = "",
    val title: String = "",
    val members: List<GroupMemberUiModel> = emptyList(),
    val payments: List<PaymentUiModel> = emptyList(),
    val balance: Map<GroupMemberUiModel, BigDecimal> = mapOf(),
    val total: String = "",
    val isCurrentUserOwner: Boolean = false
) {
    val membersForSelect: Map<GroupMemberUiModel, Boolean>
        get() = members.associateWith { it.uid == members.first().uid }

    fun getBalanceForShare() = title
        .plus("\n\n" + balance.map { entry -> "${entry.key.name} ${entry.value}" }.joinToString("\n"))
        .plus("\n\nTotal $total")

    companion object {
        fun sample() = GroupUiModel(
            id = UUID.randomUUID().toString(),
            title = "Group Sample",
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
                GroupMemberUiModel() to BigDecimal("100"),
                GroupMemberUiModel() to BigDecimal("-100"),
                GroupMemberUiModel() to BigDecimal.ZERO,
            ),
            total = "$300"
        )
    }
}

