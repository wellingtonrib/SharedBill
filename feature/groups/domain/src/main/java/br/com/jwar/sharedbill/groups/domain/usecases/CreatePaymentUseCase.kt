package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import java.util.Date

interface CreatePaymentUseCase {
    suspend operator fun invoke(
        description: String,
        value: String,
        date: Date,
        paidById: String,
        paidToIds: List<String>,
        groupId: String,
        paymentType: PaymentType
    ): Result<Payment>
}