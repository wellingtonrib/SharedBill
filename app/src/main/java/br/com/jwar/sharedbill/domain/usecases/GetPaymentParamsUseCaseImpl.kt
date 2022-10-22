package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import javax.inject.Inject

class GetPaymentParamsUseCaseImpl @Inject constructor(
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
    private val userToUserUiModelMapper: UserToUserUiModelMapper
): GetPaymentParamsUseCase {
    override fun invoke(group: Group, user: User): PaymentContract.SendPaymentParams {
        val groupUiModel = groupToGroupUiModelMapper.mapFrom(group)
        val userUiModel = userToUserUiModelMapper.mapFrom(user)
        val membersUiModels = groupUiModel.members
        return PaymentContract.SendPaymentParams(
            group = groupUiModel,
            paidBy = userUiModel,
            paidTo = membersUiModels
        )
    }
}