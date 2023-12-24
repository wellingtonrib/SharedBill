package br.com.jwar.sharedbill.groups.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.core.utility.extensions.defaultFormat
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
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

@Composable
fun PaymentUiModel.getInfo() =
    StringBuilder().apply {
        appendLine(
            stringResource(
                if (paymentType == PaymentType.EXPENSE) {
                    R.string.message_payment_split_description
                } else {
                    R.string.message_payment_description
                },
                description,
                value,
                paidBy.firstName,
                paidTo.joinToString { it.firstName },
                createdAt.defaultFormat()
            )
        )
        if (paidBy.uid != createdBy.uid) {
            appendLine(stringResource(R.string.message_payment_created_by, createdBy.firstName))
        }
    }.toString()

@Composable
fun PaymentUiModel.getMessage(group: GroupUiModel) =
    when {
        paymentType == PaymentType.REFUND -> {
            stringResource(R.string.message_payment_refund, paidTo.joinToString(limit = 3, truncated = "...") { it.firstName })
        }
        paidTo.size == group.members.size -> {
            stringResource(R.string.message_payment_detail_to_all, paidBy.firstName)
        }
        else -> {
            stringResource(R.string.message_payment_detail, paidBy.firstName, paidTo.joinToString(limit = 3, truncated = "...") { it.firstName })

        }
    }