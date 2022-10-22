package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.SendPaymentParams
import com.google.firebase.Timestamp
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class SendPaymentUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): SendPaymentUseCase {
    override suspend fun invoke(
        params: SendPaymentParams
    ): Flow<Resource<Group>> =
        groupRepository.getGroupById(params.group.id, refresh = true)
            .flatMapLatest { groupResource ->
                if (groupResource is Resource.Success)
                    sendPayment(params, groupResource.data)
                else flowOf(groupResource)
            }

    private suspend fun sendPayment(
        params: SendPaymentParams,
        group: Group
    ): Flow<Resource<Group>> = with(params) {
        val paidBy = group.findMemberByUid(paidBy.uid)
            ?: return flowOf(Resource.Failure(PaymentException.PayerNotInGroupException))
        val paidTo = group.members.filter { member ->
            paidTo.map { it.uid }.contains(member.uid)
        }

        return groupRepository.sendPayment(
            payment = Payment(
                description = description,
                value = value,
                paidBy = paidBy,
                paidTo = paidTo,
                createdAt = Timestamp(date)
            ),
            groupId = group.id
        )
    }
}