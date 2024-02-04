package br.com.jwar.sharedbill.groups.presentation.mappers

import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.groups.domain.model.Payment

interface PaymentToPaymentUiModelMapper {
    fun mapFrom(from: Payment, group: Group): PaymentUiModel
}