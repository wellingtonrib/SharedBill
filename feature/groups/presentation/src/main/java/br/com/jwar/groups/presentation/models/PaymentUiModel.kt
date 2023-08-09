package br.com.jwar.groups.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import java.util.UUID

class PaymentUiModel(
    val id: String = "",
    val description: String = "",
    val value: String = "",
    val paidBy: String = "",
    val paidTo: String = "",
    val createdAt: String = "",
    val createdBy: String = "",
    val paymentType: PaymentType = PaymentType.EXPENSE,
) {
    @Composable
    fun getInfo() = StringBuilder().apply {
        append(stringResource(R.string.message_payment_description, description + "\n"))
        append(stringResource(R.string.message_payment_value, value + "\n"))
        append(stringResource(R.string.message_payment_paid_by, paidBy) + "\n")
        append(stringResource(R.string.message_payment_paid_to, paidTo) + "\n")
        append(stringResource(R.string.message_payment_created_by, createdBy) + "\n")
        append(stringResource(R.string.message_payment_created_at, createdAt))
    }.toString()

    @Composable
    fun getMessage(group: GroupUiModel) =
        if (paidTo.split(",").size == group.membersNames.split(",").size) {
            stringResource(R.string.message_payment_detail_to_all, paidBy)
        } else {
            stringResource(R.string.message_payment_detail, paidBy, paidTo)
        }

    companion object {
        fun sample() = PaymentUiModel(
            id = UUID.randomUUID().toString(),
            description = "Payment description",
            value = "100.00",
            paidBy = "Member One",
            paidTo = "Member One, Member Two",
            createdAt = "00/00",
            createdBy = "Member One",
            paymentType = PaymentType.EXPENSE,
        )
    }
}