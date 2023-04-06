package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.core.utility.extensions.toBigDecimalOrZero
import br.com.jwar.sharedbill.groups.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import com.google.firebase.Timestamp
import java.math.BigDecimal
import java.util.*

class CreatePaymentUseCaseImpl(
    private val groupRepository: GroupRepository
) : CreatePaymentUseCase {
    override suspend fun invoke(
        description: String,
        value: String,
        date: Date,
        paidById: String,
        paidToIds: List<String>,
        groupId: String,
    ): Result<Payment> = resultOf {
        if (description.isEmpty()) throw PaymentException.EmptyDescriptionException
        if (value.toBigDecimalOrZero() == BigDecimal.ZERO) throw PaymentException.InvalidValueException
        if (paidToIds.isEmpty()) throw PaymentException.EmptyRelatedMembersException

        val group = groupRepository.getGroupById(groupId, true)
        val paidBy = group.findMemberById(paidById)
            ?: throw PaymentException.PayerNotInGroupException
        val createdBy = group.findCurrentUser()
            ?: throw PaymentException.CurrentUserNotInGroupException
        val paidTo = group.members.filter { member ->
            paidToIds.contains(member.id)
        }

        val payment = Payment(
            groupId = group.id,
            id = UUID.randomUUID().toString(),
            description = description,
            value = value,
            paidBy = paidBy,
            paidTo = paidTo,
            createdAt = Timestamp(date),
            createdBy = createdBy
        )
        return Result.success(payment)
    }
}