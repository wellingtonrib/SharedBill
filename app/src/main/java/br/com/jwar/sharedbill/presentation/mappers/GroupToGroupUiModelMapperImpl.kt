package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.core.extensions.toBigDecimalOrZero
import br.com.jwar.sharedbill.core.extensions.toCurrency
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import javax.inject.Inject

class GroupToGroupUiModelMapperImpl @Inject constructor(
    private val paymentToPaymentUiModelMapper: PaymentToPaymentUiModelMapper,
    private val userToUserUiModelMapper: UserToUserUiModelMapper
) : GroupToGroupUiModelMapper {
    override fun mapFrom(from: Group) =
        GroupUiModel(
            id = from.id,
            title = from.title,
            membersNames = mapMembersNames(from),
            members = mapMembers(from),
            payments = mapPayments(from),
            balance = mapBalance(from),
            total = mapTotal(from),
            isCurrentUserOwner = mapIsCurrentUserOwner(from)
        )

    private fun mapTotal(from: Group) =
        from.payments.sumOf { it.value.toBigDecimalOrZero() }.toCurrency()

    private fun mapPayments(from: Group) =
        from.payments.map { paymentToPaymentUiModelMapper.mapFrom(it) }

    private fun mapMembers(from: Group) =
        from.members.map { userToUserUiModelMapper.mapFrom(it) }

    private fun mapMembersNames(from: Group) =
        from.members.joinToString(", ") { it.firstName }

    private fun mapIsCurrentUserOwner(from: Group) =
        from.members.firstOrNull { it.isCurrentUser }?.firebaseUserId == from.owner.firebaseUserId

    private fun mapBalance(from: Group) =
        from.balance.mapNotNull {
            val member = from.findMemberById(it.key)
            if (member != null) {
                member.firstName to it.value.toBigDecimalOrZero()
            } else null
        }.associateBy({it.first}, {it.second})
}