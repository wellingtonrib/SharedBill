package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetGroupsStreamUseCaseImplTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val groupRepository: GroupRepository = mockk()
    private val exceptionHandler: ExceptionHandler = mockk(relaxed = true)
    private val getGroupsStreamUseCase = GetGroupsStreamUseCaseImpl(groupRepository, exceptionHandler)

    @Test
    fun `invoke should emit success result with groups from repository`() = runTest {
        val groups = listOf(Group("1", "Group 1"), Group("2", "Group 2"))
        coEvery { groupRepository.getGroupsStream() } returns flowOf(groups)

        val result = getGroupsStreamUseCase.invoke().toList()

        assertEquals(1, result.size)
        assertEquals(Result.success(groups), result[0])
    }

    @Test
    fun `invoke should emit failure result when an exception occurs`() = runTest {
        val exception = Exception("Failed to get groups")
        coEvery { groupRepository.getGroupsStream() } throws exception

        val result = getGroupsStreamUseCase.invoke().toList()

        assertEquals(Result.failure<List<Group>>(exception), result[0])
    }
}
