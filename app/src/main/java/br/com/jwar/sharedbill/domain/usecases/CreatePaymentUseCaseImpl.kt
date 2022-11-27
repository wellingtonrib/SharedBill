package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.extensions.resultOf
import br.com.jwar.sharedbill.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import com.google.firebase.Timestamp
import java.util.UUID

class CreatePaymentUseCaseImpl(
    private val groupRepository: GroupRepository
) : CreatePaymentUseCase {
    override suspend fun invoke(params: PaymentContract.SendPaymentParams): Result<Payment> = resultOf {
        if (params.description.isEmpty()) throw PaymentException.EmptyDescriptionException
        if (params.value.isEmpty()) throw PaymentException.EmptyValueException
        if (params.paidTo.isEmpty()) throw PaymentException.EmptyRelatedMembersException

        val group = groupRepository.getGroupById(params.group.id, true).getOrNull()
            ?: throw PaymentException.GroupNotFound
        val paidBy = group.findMemberById(params.paidBy.uid)
            ?: throw PaymentException.PayerNotInGroupException
        val createdBy = group.findCurrentUser()
            ?: throw PaymentException.CurrentUserNotInGroupException
        val paidTo = group.members.filter { member ->
            params.paidTo.map { it.uid }.contains(member.id)
        }

        val payment = Payment(
            groupId = group.id,
            id = UUID.randomUUID().toString(),
            description = params.description,
            value = params.value,
            paidBy = paidBy,
            paidTo = paidTo,
            createdAt = Timestamp(params.date),
            createdBy = createdBy
        )
        return Result.success(payment)
    }
}