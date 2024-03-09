package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals

internal class AddMemberUseCaseImplTest {

    private val groupRepository: GroupRepository = mockk()
    private val exceptionHandler: ExceptionHandler = mockk()
    private val addMemberUseCase = AddMemberUseCaseImpl(groupRepository, exceptionHandler)

    @Before
    fun setup() {
        mockkStatic(UUID::class)
    }

    @After
    fun cleanup() {
        unmockkAll()
    }

    @Test
    fun `invoke should add member to group and return user id`() = runTest {
        val userName = "John Doe"
        val groupId = "1"
        val userId = "user_id"
        every { UUID.randomUUID().toString() } returns userId
        coEvery { groupRepository.addMember(any(), any()) } returns Unit

        val result = addMemberUseCase.invoke(userName, groupId)

        assert(result.isSuccess)
        assertEquals(userId, result.getOrNull())
    }
}
