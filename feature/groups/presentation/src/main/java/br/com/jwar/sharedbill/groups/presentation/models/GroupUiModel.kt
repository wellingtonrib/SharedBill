package br.com.jwar.sharedbill.groups.presentation.models

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import java.math.BigDecimal
import java.util.UUID

data class GroupUiModel(
    val id: String = "",
    val title: String = "",
    val members: ImmutableSet<GroupMemberUiModel> = ImmutableSet.of(),
    val payments: ImmutableSet<PaymentUiModel> = ImmutableSet.of(),
    val balance: ImmutableMap<GroupMemberUiModel, BigDecimal> = ImmutableMap.of(),
    val total: String = "",
    val isCurrentUserOwner: Boolean = false
) {
    fun getBalanceForShare() = title
        .plus("\n\n" + balance.map { entry -> "${entry.key.name} ${entry.value}" }.joinToString("\n"))
        .plus("\n\nTotal $total")

    val membersToSelect: ImmutableMap<GroupMemberUiModel, Boolean>
        get() = ImmutableMap.copyOf(members.associateWith { it.uid == members.first().uid })

    companion object {
        fun sample() = GroupUiModel(
            id = UUID.randomUUID().toString(),
            title = "Group Sample",
            members = ImmutableSet.of(
                GroupMemberUiModel.sample(),
                GroupMemberUiModel.sample(),
                GroupMemberUiModel.sample(),
            ),
            payments = ImmutableSet.of(
                PaymentUiModel.sample(),
                PaymentUiModel.sample(),
                PaymentUiModel.sample(),
            ),
            balance = ImmutableMap.copyOf(
                mapOf(
                    GroupMemberUiModel.sample() to BigDecimal("100"),
                    GroupMemberUiModel.sample() to BigDecimal("-100"),
                    GroupMemberUiModel.sample() to BigDecimal.ZERO,
                )
            ),
            total = "$300"
        )
    }
}
