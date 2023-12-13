package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetGroupByIdUseCaseImplTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val groupRepository: GroupRepository = mockk()
    private val getGroupByIdUseCase = GetGroupByIdUseCaseImpl(groupRepository)

    @Test
    fun `invoke should return group from repository`() = runTest {
        val groupId = "group_id"
        val group = Group(groupId, "Group")
        coEvery { groupRepository.getGroupById(groupId, any()) } returns group

        val result = getGroupByIdUseCase.invoke(groupId, false)

        assertEquals(Result.success(group), result)
    }

    @Test
    fun `invoke should return failure result when an exception occurs`() = runTest {
        val groupId = "group_id"
        val exception = Exception("Failed to get group")
        coEvery { groupRepository.getGroupById(groupId, any()) } throws exception

        val result = getGroupByIdUseCase.invoke(groupId, false)

        assertEquals(Result.failure<Group>(exception), result)
    }

}
