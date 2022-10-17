package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.core.DATE_FORMAT_SMALL
import br.com.jwar.sharedbill.core.format
import br.com.jwar.sharedbill.core.toCurrency
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.presentation.models.PaymentUiModel
import javax.inject.Inject

class PaymentToPaymentUiModelMapperImpl @Inject constructor(): PaymentToPaymentUiModelMapper {
    override fun mapFrom(from: Payment) =
        PaymentUiModel(
            description = from.description,
            value = from.value.toCurrency(),
            paidBy = from.paidBy.getFirstName(),
            paidTo = from.paidTo.joinToString(", ") { it.getFirstName() },
            createdAt = from.createdAt.format(DATE_FORMAT_SMALL),
            createdBy = from.createdBy.getFirstName()
        )
}