package br.com.jwar.groups.presentation.mappers

import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.groups.domain.model.Payment

interface PaymentToPaymentUiModelMapper {
    fun mapFrom(from: Payment): PaymentUiModel
}