package br.com.jwar.sharedbill.presentation.mappers

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
            membersNames = from.members.joinToString(", ") { it.getFirstName() },
            members = from.members.map { userToUserUiModelMapper.mapFrom(it) },
            payments = from.payments.map { paymentToPaymentUiModelMapper.mapFrom(it) },
            balance = mapBalance(from)
        )

    private fun mapBalance(from: Group): Map<String, BigDecimal> {
        return from.balance.map {
            val member = from.findMemberByUid(it.key)
            member?.getFirstName().orEmpty() to it.value.toBigDecimal()
        }.associateBy({it.first}, {it.second})
    }
}