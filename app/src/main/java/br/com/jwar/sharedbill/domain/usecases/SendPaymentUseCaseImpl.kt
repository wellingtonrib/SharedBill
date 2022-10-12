package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendPaymentUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): SendPaymentUseCase {
    override suspend fun invoke(
        params: PaymentContract.Event.SendPaymentParams
    ): Flow<Resource<Group>> {
        return groupRepository.sendPayment(
            with(params) {
                Payment(
                    description = description,
                    value = value,
                    paidBy = paidBy ?: group.members.first(),
                    paidTo = paidTo.ifEmpty { group.members },
                    createdAt = Timestamp(date)
                )
            },
            group = params.group
        )
    }
}