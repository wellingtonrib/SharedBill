package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import com.google.firebase.Timestamp
import java.util.*

class CreatePaymentUseCaseImpl(
    private val groupRepository: GroupRepository
) : CreatePaymentUseCase {
    override suspend fun invoke(params: PaymentContract.SendPaymentParams): Result<Payment> {
        if (params.description.isEmpty()) return Result.failure(PaymentException.EmptyDescriptionException)
        if (params.value.isEmpty()) return Result.failure(PaymentException.EmptyValueException)
        if (params.paidTo.isEmpty()) return Result.failure(PaymentException.EmptyRelatedMembersException)

        val group = groupRepository.getGroupById(params.group.id, true).getOrNull()
            ?: return Result.failure(PaymentException.GroupNotFound)
        val paidBy = group.findMemberById(params.paidBy.uid)
            ?: return Result.failure(PaymentException.PayerNotInGroupException)
        val createdBy = group.findCurrentUser()
            ?: return Result.failure(PaymentException.CurrentUserNotInGroupException)
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