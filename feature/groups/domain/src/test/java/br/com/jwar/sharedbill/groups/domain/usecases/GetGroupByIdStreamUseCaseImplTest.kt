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
class GetGroupByIdStreamUseCaseImplTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val groupRepository: GroupRepository = mockk()
    private val exceptionHandler: ExceptionHandler = mockk()
    private val getGroupByIdStreamUseCase = GetGroupByIdStreamUseCaseImpl(groupRepository, exceptionHandler)

    @Test
    fun `invoke should return group stream from repository`() = runTest {
        val groupId = "group_id"
        val group = Group(groupId, "Group")
        coEvery { groupRepository.getGroupByIdStream(any()) } returns flowOf(group)

        val result = getGroupByIdStreamUseCase.invoke(groupId).toList()

        assertEquals(listOf(Result.success(group)), result)
    }

    @Test(expected = java.lang.Exception::class)
    fun `invoke should return failure result when an exception occurs`() = runTest {
        val groupId = "group_id"
        val exception = Exception("Failed to get group")
        coEvery { groupRepository.getGroupByIdStream(any()) } throws exception

        val result = getGroupByIdStreamUseCase.invoke(groupId).toList()

        assertEquals(listOf(Result.failure<Group>(exception)), result)
    }
}
