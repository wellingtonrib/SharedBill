package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.groups.domain.model.Payment

interface SendPaymentUseCase {
    suspend operator fun invoke(
        payment: Payment
    ): Result<Unit>
}