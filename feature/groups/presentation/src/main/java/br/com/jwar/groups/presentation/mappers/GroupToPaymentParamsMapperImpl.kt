package br.com.jwar.groups.presentation.mappers

import br.com.jwar.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import java.util.Date
import javax.inject.Inject

class GroupToPaymentParamsMapperImpl @Inject constructor(
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
    private val userToGroupMemberUiModelMapper: UserToGroupMemberUiModelMapper,
    private val stringProvider: StringProvider,
    ): GroupToPaymentParamsMapper {
    override fun mapFrom(
        group: Group,
        paymentType: PaymentType,
    ): PaymentContract.PaymentParams {
        val groupUiModel = groupToGroupUiModelMapper.mapFrom(group)
        val paidBy = userToGroupMemberUiModelMapper.mapFrom(group.findCurrentUser() ?: group.owner)
        val paidTo =
            if (paymentType == PaymentType.EXPENSE) groupUiModel.members else groupUiModel.members.take(1)
        val description =
            if (paymentType == PaymentType.EXPENSE) "" else stringProvider.getString(R.string.label_settlement)

        return PaymentContract.PaymentParams(
            group = groupUiModel,
            description = description,
            date = Date(),
            paidBy = paidBy,
            paidTo = paidTo,
            paymentType = paymentType
        )
    }
}