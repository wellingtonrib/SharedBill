package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract

interface GetPaymentParamsUseCase {
    operator fun invoke(group: Group, user: User): PaymentContract.SendPaymentParams
}