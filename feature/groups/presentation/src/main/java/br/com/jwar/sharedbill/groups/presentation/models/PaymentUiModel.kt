package br.com.jwar.sharedbill.groups.presentation.models

import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import com.google.common.collect.ImmutableSet
import java.util.Date
import java.util.UUID

data class PaymentUiModel(
    val id: String = "",
    val description: String = "",
    val value: String = "",
    val paidBy: GroupMemberUiModel = GroupMemberUiModel(),
    val paidTo: ImmutableSet<GroupMemberUiModel> = ImmutableSet.of(),
    val createdAt: Date = Date(),
    val createdBy: GroupMemberUiModel = GroupMemberUiModel(),
    val paymentType: PaymentType = PaymentType.EXPENSE,
) {
    companion object {
        fun sample() = PaymentUiModel(
            id = UUID.randomUUID().toString(),
            description = "Payment description",
            value = "100.00",
            paidBy = GroupMemberUiModel.sample(),
            paidTo = ImmutableSet.of(GroupMemberUiModel.sample()),
            createdAt = Date(),
            createdBy = GroupMemberUiModel.sample(),
            paymentType = PaymentType.EXPENSE,
        )
    }
}