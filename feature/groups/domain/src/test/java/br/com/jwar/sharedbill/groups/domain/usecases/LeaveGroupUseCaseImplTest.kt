package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.groups.domain.usecases.LeaveGroupUseCaseImpl
import br.com.jwar.sharedbill.groups.domain.usecases.RemoveMemberUseCase
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
class LeaveGroupUseCaseImplTest {

    private val groupsRepository: GroupRepository = mockk()
    private val removeMemberUseCase: RemoveMemberUseCase = mockk()
    private val leaveGroupUseCase = LeaveGroupUseCaseImpl(groupsRepository, removeMemberUseCase)

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Test
    fun `invoke should remove member and return null when successful`() = runTest {
        val groupId = "1"
        val currentUser = User("1", isCurrentUser = true)
        val group = Group(groupId, members = listOf(currentUser))
        prepareScenario(group = group)

        val result = leaveGroupUseCase.invoke(groupId)

        coVerify { removeMemberUseCase(currentUser.id, groupId) }
        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun `invoke should throw MemberNotFoundException when current user is not found`() = runTest {
        val groupId = "1"
        val currentUser = User("1", isCurrentUser = false)
        val group = Group(groupId, members = listOf(currentUser))
        prepareScenario(group = group)

        val result = leaveGroupUseCase.invoke(groupId)

        assertIs<GroupException.MemberNotFoundException>(result.exceptionOrNull())
    }

    private fun prepareScenario(
        group: Group = mockk(),
    ) {
        coEvery { groupsRepository.getGroupById(any()) } returns group
        coEvery { removeMemberUseCase(any(), any()) } returns Result.success(Unit)
    }
}
