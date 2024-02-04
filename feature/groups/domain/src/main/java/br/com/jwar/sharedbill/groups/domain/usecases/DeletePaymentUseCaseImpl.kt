package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
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
            groupRepository.deletePayment(payment, refundSuffix)
        }
    }
}