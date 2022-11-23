package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Result

interface SendPaymentUseCase {
    suspend operator fun invoke(
        payment: Payment
    ): Result<Payment>
}