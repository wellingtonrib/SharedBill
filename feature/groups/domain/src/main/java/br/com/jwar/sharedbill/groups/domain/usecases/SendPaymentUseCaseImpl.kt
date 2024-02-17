package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import javax.inject.Inject

class SendPaymentUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val exceptionHandler: ExceptionHandler,
): SendPaymentUseCase {
    override suspend fun invoke(payment: Payment) = resultOf(exceptionHandler) {
        groupRepository.sendPayment(payment)
    }
}
