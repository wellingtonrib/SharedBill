package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import java.math.BigDecimal
import javax.inject.Inject

class GroupToGroupUiModelMapperImpl @Inject constructor(
    private val paymentToPaymentUiModelMapper: PaymentToPaymentUiModelMapper
) : GroupToGroupUiModelMapper {
    override fun mapFrom(from: Group) =
        GroupUiModel(
            id = from.id,
            title = from.title,
            members = from.members.joinToString(", ") { it.firstName },
            payments = from.payments.map { paymentToPaymentUiModelMapper.mapFrom(it) },
            balance = mapBalance(from)
        )

    private fun mapBalance(from: Group): Map<String, BigDecimal> {
        return from.balance.map {
            from.findMemberByUid(it.key)?.firstName.orEmpty() to it.value.toBigDecimal()
        }.associateBy({it.first}, {it.second})
    }

}