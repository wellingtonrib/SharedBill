package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.SendPaymentParams
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import java.util.*

class SendPaymentUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val getGroupByIdWithCurrentMemberUseCase: GetGroupByIdWithCurrentMemberUseCase
): SendPaymentUseCase {
    override suspend fun invoke(
        params: SendPaymentParams
    ): Flow<Resource<Any>> {
        return getGroupByIdWithCurrentMemberUseCase(params.group.id)
            .flatMapLatest { resource ->
                if (resource is Resource.Success) {
                    val (group, currentUser) = resource.data
                    sendPayment(params, group, currentUser)
                }
                else flowOf(resource)
            }
    }

    private suspend fun sendPayment(
        params: SendPaymentParams,
        group: Group,
        currentUser: User
    ): Flow<Resource<Group>> = with(params) {
        val paidBy = group.findMemberByUid(paidBy.uid)
            ?: return flowOf(Resource.Failure(PaymentException.PayerNotInGroupException))
        val createdBy = group.findMemberByFirebaseId(currentUser.firebaseUserId)
            ?: return flowOf(Resource.Failure(PaymentException.CurrentUserNotInGroupException))
        val paidTo = group.members.filter { member ->
            paidTo.map { it.uid }.contains(member.uid)
        }

        return groupRepository.sendPayment(
            payment = Payment(
                groupId = group.id,
                id = UUID.randomUUID().toString(),
                description = description,
                value = value,
                paidBy = paidBy,
                paidTo = paidTo,
                createdAt = Timestamp(date),
                createdBy = createdBy
            ),
            groupId = group.id
        )
    }
}