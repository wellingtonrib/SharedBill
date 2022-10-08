package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import com.google.firebase.Timestamp
import javax.inject.Inject

class SendPaymentUseCaseImpl @Inject constructor(
    private val groupsRepository: GroupsRepository
): SendPaymentUseCase {
    override suspend fun invoke(
        params: PaymentContract.Event.SendPaymentParams
    ) =
        groupsRepository.sendPayment(
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