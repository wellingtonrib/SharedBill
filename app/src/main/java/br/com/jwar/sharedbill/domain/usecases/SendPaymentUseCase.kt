package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import kotlinx.coroutines.flow.Flow

interface SendPaymentUseCase {
    suspend operator fun invoke(
        params: PaymentContract.SendPaymentParams
    ): Flow<Resource<Any>>
}