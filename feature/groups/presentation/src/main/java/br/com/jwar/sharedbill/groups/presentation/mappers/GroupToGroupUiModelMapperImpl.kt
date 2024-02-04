package br.com.jwar.sharedbill.groups.presentation.mappers

import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.utility.extensions.toBigDecimalOrZero
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import javax.inject.Inject

class GroupToGroupUiModelMapperImpl @Inject constructor(
    private val paymentToPaymentUiModelMapper: PaymentToPaymentUiModelMapper,
    private val userToGroupMemberUiModelMapper: UserToGroupMemberUiModelMapper
) : GroupToGroupUiModelMapper {
    override fun mapFrom(from: Group) =
        GroupUiModel(
            id = from.id,
            title = from.title,
            members = mapMembers(from),
            payments = mapPayments(from),
            balance = mapBalance(from),
            total = mapTotal(from),
            isCurrentUserOwner = mapIsCurrentUserOwner(from)
        )

    private fun mapTotal(from: Group) = from.payments
        .filter { it.paymentType == PaymentType.EXPENSE }
        .filterNot { it.paymentType == PaymentType.REVERSE }
        .filterNot { it.reversed ?: false }
        .sumOf { it.value.toBigDecimalOrZero() }.toCurrency()

    private fun mapPayments(from: Group) = ImmutableSet.copyOf(
        from.payments
            .filterNot { it.paymentType == PaymentType.REVERSE }
            .filterNot { it.reversed ?: false }
            .sortedByDescending { it.createdAt }
            .map { paymentToPaymentUiModelMapper.mapFrom(it, from) }
    )

    private fun mapMembers(from: Group) = ImmutableSet.copyOf(
        from.members
            .sortedByDescending { it.isCurrentUser }
            .map { userToGroupMemberUiModelMapper.mapFrom(it) }
    )

    private fun mapIsCurrentUserOwner(from: Group) =
        from.members.firstOrNull { it.isCurrentUser }?.firebaseUserId == from.owner.firebaseUserId

    private fun mapBalance(from: Group) = ImmutableMap.copyOf(
        from.balance.mapNotNull {
            val member = from.findMemberById(it.key)
            if (member != null) {
                userToGroupMemberUiModelMapper.mapFrom(member) to it.value.toBigDecimalOrZero()
            } else null
        }.associateBy({it.first}, {it.second})
    )
}