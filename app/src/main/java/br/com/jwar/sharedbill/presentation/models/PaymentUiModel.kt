package br.com.jwar.sharedbill.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.core.extensions.replaceIf
import java.util.*

class PaymentUiModel(
    val id: String = "",
    val description: String = "",
    val value: String = "",
    val paidBy: String = "",
    val paidTo: String = "",
    val createdAt: String = "",
    val createdBy: String = "",
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
    fun getMessage(group: GroupUiModel) = stringResource(
        R.string.message_payment_detail,
        paidBy, paidTo.replaceIf(stringResource(R.string.message_paid_to_all)) {
            paidTo.split(",").size == group.membersNames.split(",").size
        }
    )

    companion object {
        fun sample() = PaymentUiModel(
            id = UUID.randomUUID().toString(),
            description = "Payment description",
            value = "100.00",
            paidBy = "Member One",
            paidTo = "Member One, Member Two",
            createdBy = "Member One",
            createdAt = "00/00"
        )
    }
}