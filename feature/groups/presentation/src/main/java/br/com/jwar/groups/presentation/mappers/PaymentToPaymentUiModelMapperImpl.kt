package br.com.jwar.groups.presentation.mappers

import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.core.utility.extensions.DATE_FORMAT_SMALL
import br.com.jwar.sharedbill.core.utility.extensions.format
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.domain.model.Payment
import javax.inject.Inject

class PaymentToPaymentUiModelMapperImpl @Inject constructor(): PaymentToPaymentUiModelMapper {
    override fun mapFrom(from: Payment) =
        PaymentUiModel(
            description = from.description,
            value = from.value.toCurrency(),
            paidBy = from.paidBy.firstName,
            paidTo = from.paidTo.joinToString(", ") { it.firstName },
            createdAt = from.createdAt.format(DATE_FORMAT_SMALL),
            createdBy = from.createdBy.firstName,
            paymentType = from.paymentType,
        )
}