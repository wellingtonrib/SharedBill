package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Payment

interface SendPaymentUseCase {
    suspend operator fun invoke(
        payment: Payment
    ): Result<Unit>
}