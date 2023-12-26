package br.com.jwar.sharedbill.groups.presentation.mappers

import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import com.google.common.collect.ImmutableSet
import javax.inject.Inject

class PaymentToPaymentUiModelMapperImpl @Inject constructor(
    private val userToUserUiModelMapper: UserToGroupMemberUiModelMapper
): PaymentToPaymentUiModelMapper {
    override fun mapFrom(from: Payment, group: Group) =
        PaymentUiModel(
            id = from.id,
            description = from.description,
            value = from.value.toCurrency(),
            paidBy = mapMemberById(from.paidBy, group),
            paidTo = mapPaidTo(from, group),
            createdAt = from.createdAt,
            createdBy = mapMemberById(from.createdBy, group),
            paymentType = from.paymentType,
            isReversed = from.reversed ?: false,
        )

    private fun mapPaidTo(from: Payment, group: Group): ImmutableSet<GroupMemberUiModel> {
        return ImmutableSet.copyOf(from.paidTo.mapNotNull { (memberId, _) ->
            mapMemberById(memberId, group)
        })
    }

    private fun mapMemberById(memberId: String, group: Group) =
        group.findMemberById(memberId)?.let { member ->
            userToUserUiModelMapper.mapFrom(member)
        } ?: GroupMemberUiModel()
}