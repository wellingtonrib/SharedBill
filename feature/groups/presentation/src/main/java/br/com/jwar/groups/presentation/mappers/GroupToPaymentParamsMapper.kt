package br.com.jwar.groups.presentation.mappers

import br.com.jwar.groups.presentation.screens.payment.PaymentContract
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.PaymentType

interface GroupToPaymentParamsMapper {
    fun mapFrom(group: Group, paymentType: PaymentType): PaymentContract.PaymentParams
}