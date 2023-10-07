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
    val paidBy: User = User(),
    val paidTo: List<User> = emptyList(),
    val createdAt: Date = Date(),
    val createdBy: User = User(),
    val paymentType: PaymentType = PaymentType.EXPENSE,
)

enum class PaymentType {
    EXPENSE,
    SETTLEMENT
}