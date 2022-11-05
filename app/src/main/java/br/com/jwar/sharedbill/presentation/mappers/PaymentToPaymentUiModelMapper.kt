package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.presentation.models.PaymentUiModel

interface PaymentToPaymentUiModelMapper {
    fun mapFrom(from: Payment): PaymentUiModel
}