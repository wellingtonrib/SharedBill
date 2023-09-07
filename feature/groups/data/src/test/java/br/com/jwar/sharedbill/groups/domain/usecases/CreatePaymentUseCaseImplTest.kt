package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Date

@ExperimentalCoroutinesApi
class CreatePaymentUseCaseImplTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val groupRepository: GroupRepository = mockk()
    private val createPaymentUseCase = CreatePaymentUseCaseImpl(groupRepository)

    @Test
    fun `invoke should create payment and return success result`() = runTest {
        val description = "Payment description"
        val value = "100.00"
        val dateTime = Date().time
        val paidById = "payer_id"
        val paidToIds = listOf("member_id_1", "member_id_2")
        val groupId = "group_id"
        val payer = User(id = paidById, isCurrentUser = true)
        val members = listOf(User(id = "member_id_1"), User(id = "member_id_2"))
        val group = spyk(Group(id = groupId, title = "Group", members = listOf(payer) + members))
        coEvery { groupRepository.getGroupById(any(), any()) } returns group

        val result = createPaymentUseCase.invoke(
            description = description,
            value = value,
            dateTime = dateTime,
            paidById = paidById,
            paidToIds = paidToIds,
            groupId = groupId,
            paymentType = PaymentType.EXPENSE,
        )

        val expectedPayment = Payment(
            groupId = groupId,
            id = result.getOrNull()?.id ?: "",
            description = description,
            value = value,
            paidBy = payer,
            paidTo = members,
            createdAt = Date(dateTime),
            createdBy = payer
        )

        assertEquals(Result.success(expectedPayment), result)
    }
}
