package br.com.jwar.sharedbill.groups.domain.model

import androidx.annotation.Keep
import br.com.jwar.sharedbill.account.domain.model.User
import java.util.*

@Keep
data class Payment(
    val groupId: String = "",
    val id: String = "",
    val description: String = "",
    val value: String = "",
    val paidBy: String = "",
    val paidTo: Map<String, Int> = mapOf(),
    val createdAt: Date = Date(),
    val createdBy: String = "",
    val paymentType: PaymentType = PaymentType.EXPENSE,
)

enum class PaymentType {
    EXPENSE,
    SETTLEMENT,
    REFUND
}