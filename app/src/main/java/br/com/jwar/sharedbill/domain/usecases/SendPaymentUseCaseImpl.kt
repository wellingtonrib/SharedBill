package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class SendPaymentUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): SendPaymentUseCase {
    override suspend fun invoke(payment: Payment) =
        groupRepository.sendPayment(payment)
            .mapSuccess { payment }
    }
