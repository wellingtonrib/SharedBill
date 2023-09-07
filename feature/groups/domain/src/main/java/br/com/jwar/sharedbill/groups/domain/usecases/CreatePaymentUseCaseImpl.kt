package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.core.utility.extensions.toBigDecimalOrZero
import br.com.jwar.sharedbill.groups.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import java.math.BigDecimal
import java.util.Calendar
import java.util.Date
import java.util.UUID

class CreatePaymentUseCaseImpl(
    private val groupRepository: GroupRepository,
) : CreatePaymentUseCase {
    override suspend fun invoke(
        description: String,
        value: String,
        dateTime: Long,
        paidById: String,
        paidToIds: List<String>,
        groupId: String,
        paymentType: PaymentType,
    ): Result<Payment> = resultOf {
        if (description.isEmpty()) throw PaymentException.InvalidDescriptionException
        if (value.toBigDecimalOrZero() == BigDecimal.ZERO) throw PaymentException.InvalidValueException
        if (paidToIds.isEmpty()) throw PaymentException.InvalidPaidToException

        val date = Date(dateTime)
        if (date.after(Calendar.getInstance().time)) throw PaymentException.InvalidDateException

        val group = groupRepository.getGroupById(groupId, true)
        val paidBy = group.findMemberById(paidById)
            ?: throw PaymentException.InvalidPaidByException
        val createdBy = group.findCurrentUser()
            ?: throw PaymentException.InvalidPaidByException
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
            createdAt = Date(dateTime),
            createdBy = createdBy,
            paymentType = paymentType,
        )
        return Result.success(payment)
    }
}