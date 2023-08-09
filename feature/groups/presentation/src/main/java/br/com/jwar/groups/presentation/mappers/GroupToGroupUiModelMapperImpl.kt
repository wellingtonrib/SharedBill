package br.com.jwar.groups.presentation.mappers

import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.utility.extensions.toBigDecimalOrZero
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import javax.inject.Inject

class GroupToGroupUiModelMapperImpl @Inject constructor(
    private val paymentToPaymentUiModelMapper: PaymentToPaymentUiModelMapper,
    private val userToGroupMemberUiModelMapper: UserToGroupMemberUiModelMapper
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
        from.payments
            .filter { it.paymentType == PaymentType.EXPENSE }
            .sumOf { it.value.toBigDecimalOrZero() }.toCurrency()

    private fun mapPayments(from: Group) =
        from.payments.sortedByDescending { it.createdAt }.map { paymentToPaymentUiModelMapper.mapFrom(it) }

    private fun mapMembers(from: Group) =
        from.members.map { userToGroupMemberUiModelMapper.mapFrom(it) }

    private fun mapMembersNames(from: Group) =
        from.members.joinToString(", ") { it.firstName }

    private fun mapIsCurrentUserOwner(from: Group) =
        from.members.firstOrNull { it.isCurrentUser }?.firebaseUserId == from.owner.firebaseUserId

    private fun mapBalance(from: Group) =
        from.balance.mapNotNull {
            val member = from.findMemberById(it.key)
            if (member != null) {
                userToGroupMemberUiModelMapper.mapFrom(member) to it.value.toBigDecimalOrZero()
            } else null
        }.associateBy({it.first}, {it.second})
}