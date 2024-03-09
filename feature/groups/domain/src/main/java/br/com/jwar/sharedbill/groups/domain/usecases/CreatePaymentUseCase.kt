package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType

interface CreatePaymentUseCase {
    suspend operator fun invoke(
        description: String,
        value: String,
        dateTime: Long,
        paidById: String,
        paidToIds: Map<String, Int>,
        groupId: String,
        paymentType: PaymentType
    ): Result<Payment>
}
