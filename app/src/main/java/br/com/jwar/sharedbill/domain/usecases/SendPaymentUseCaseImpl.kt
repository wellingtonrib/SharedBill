package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import javax.inject.Inject

class SendPaymentUseCaseImpl @Inject constructor(
    private val groupsRepository: GroupsRepository
): SendPaymentUseCase {
    override suspend fun invoke(payment: Payment, group: Group) =
        groupsRepository.sendPayment(payment, group)
}