package br.com.jwar.groups.presentation.mappers

import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.domain.model.Payment
import javax.inject.Inject

class PaymentToPaymentUiModelMapperImpl @Inject constructor(
    private val userToUserUiModelMapper: UserToGroupMemberUiModelMapper
): PaymentToPaymentUiModelMapper {
    override fun mapFrom(from: Payment) =
        PaymentUiModel(
            description = from.description,
            value = from.value.toCurrency(),
            paidBy = userToUserUiModelMapper.mapFrom(from.paidBy),
            paidTo = from.paidTo.map { userToUserUiModelMapper.mapFrom(it) },
            createdAt = from.createdAt,
            createdBy = userToUserUiModelMapper.mapFrom(from.createdBy),
            paymentType = from.paymentType,
        )
}