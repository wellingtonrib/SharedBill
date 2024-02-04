package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Date

@ExperimentalCoroutinesApi
class SendPaymentUseCaseImplTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val groupRepository: GroupRepository = mockk()
    private val sendPaymentUseCase = SendPaymentUseCaseImpl(groupRepository)

    @Test
    fun `invoke should send payment successfully`() = runTest {
        val payment = Payment(
            groupId = "1",
            id = "1",
            description = "Test Payment",
            value = "10",
            paidBy = "User 1",
            paidTo = mapOf("User 1" to 1, "User 2" to 1),
            createdAt = Date(),
            createdBy = "User 1"
        )
        coEvery { groupRepository.sendPayment(payment) } returns Unit

        val result = sendPaymentUseCase.invoke(payment)

        coVerify { groupRepository.sendPayment(payment) }
        assertEquals(Unit, result.getOrNull())
    }
}
