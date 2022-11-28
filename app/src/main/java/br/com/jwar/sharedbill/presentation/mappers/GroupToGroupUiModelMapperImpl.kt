package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.core.extensions.toCurrency
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import java.math.BigDecimal
import javax.inject.Inject

class GroupToGroupUiModelMapperImpl @Inject constructor(
    private val paymentToPaymentUiModelMapper: PaymentToPaymentUiModelMapper,
    private val userToUserUiModelMapper: UserToUserUiModelMapper
) : GroupToGroupUiModelMapper {
    override fun mapFrom(from: Group) =
        GroupUiModel(
            id = from.id,
            title = from.title,
            membersNames = from.members.joinToString(", ") { it.firstName },
            members = from.members.map { userToUserUiModelMapper.mapFrom(it) },
            payments = from.payments.map { paymentToPaymentUiModelMapper.mapFrom(it) },
            balance = mapBalance(from),
            total = from.payments.sumOf { it.value.toBigDecimal() }.toCurrency()
        )

    private fun mapBalance(from: Group): Map<String, BigDecimal> {
        return from.balance.mapNotNull {
            val member = from.findMemberById(it.key)
            if (member != null) {
                member.firstName to it.value.toBigDecimal()
            } else null
        }.associateBy({it.first}, {it.second})
    }
}