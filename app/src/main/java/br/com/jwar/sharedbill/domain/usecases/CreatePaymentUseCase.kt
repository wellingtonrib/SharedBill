package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract

interface CreatePaymentUseCase {
    suspend operator fun invoke(
        params: PaymentContract.PaymentParams
    ): Result<Payment>
}