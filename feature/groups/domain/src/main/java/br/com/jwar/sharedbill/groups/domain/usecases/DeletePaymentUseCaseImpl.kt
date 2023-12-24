package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import java.math.RoundingMode
import java.util.UUID
import javax.inject.Inject

class DeletePaymentUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
): DeletePaymentUseCase {
    override suspend fun invoke(
        paymentId: String,
        groupId: String,
        refundSuffix: String,
    ): Result<Unit> = resultOf {
        val group = groupRepository.getGroupById(groupId)
        val payment = group.payments.find { it.id == paymentId }
        payment?.let {
            val weights = it.paidTo.values.sum()
            payment.paidTo.forEach { (memberId, weight) ->
                val refundValue = payment.value.toBigDecimal()
                    .multiply(weight.toBigDecimal())
                    .div(weights.toBigDecimal())
                    .setScale(2, RoundingMode.HALF_EVEN)
                val refundPayment = Payment(
                    groupId = groupId,
                    id = UUID.randomUUID().toString(),
                    description = payment.description.plus(" ($refundSuffix)") ,
                    value = refundValue.toString(),
                    paidBy = memberId,
                    paidTo = mapOf(payment.paidBy to 1),
                    paymentType = PaymentType.REFUND
                )
                groupRepository.sendPayment(refundPayment)
            }
        }
    }
}