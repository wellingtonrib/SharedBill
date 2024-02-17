package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
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
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
class UpdateGroupUseCaseImplTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val groupRepository: GroupRepository = mockk()
    private val exceptionHandler: ExceptionHandler = mockk(relaxed = true)
    private val updateGroupUseCase = UpdateGroupUseCaseImpl(groupRepository, exceptionHandler)

    @Test
    fun `invoke should update group successfully`() = runTest {
        val groupId = "1"
        val title = "New Group Title"
        prepareScenario()

        val result = updateGroupUseCase.invoke(groupId, title)

        coVerify { groupRepository.updateGroup(groupId, title) }
        assertEquals(Unit, result.getOrNull())
    }

    @Test
    fun `invoke should throw InvalidTitle exception when title is blank`() = runTest {
        val groupId = "1"
        val title = ""
        prepareScenario()

        val result = updateGroupUseCase.invoke(groupId, title)

        assertIs<GroupException.InvalidTitle>(result.exceptionOrNull())
    }

    private fun prepareScenario() {
        coEvery { groupRepository.updateGroup(any(), any()) } returns Unit
    }
}
