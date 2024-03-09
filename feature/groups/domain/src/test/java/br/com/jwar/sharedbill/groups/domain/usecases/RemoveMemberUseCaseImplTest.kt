package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
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
import java.util.UUID
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
class RemoveMemberUseCaseImplTest {

    private val groupRepository: GroupRepository = mockk()
    private val removeMemberUseCase = RemoveMemberUseCaseImpl(groupRepository)

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Test
    fun `invoke should remove member successfully`() = runTest {
        val groupId = "1"
        val ownerId = "1"
        val userId = "2"
        val group = createGroupWithMembers(groupId, listOf(ownerId, userId))
        prepareScenario(group)

        val result = removeMemberUseCase.invoke(userId, groupId)

        assertEquals(Unit, result.getOrNull())
    }

    @Test
    fun `invoke should throw MemberNotFoundException when member is not found`() = runTest {
        val groupId = "1"
        val ownerId = "1"
        val userId = "2"
        val group = createGroupWithMembers(groupId, listOf(ownerId))
        prepareScenario(group)

        val result = removeMemberUseCase.invoke(userId, groupId)

        assertIs<GroupException.MemberNotFoundException>(result.exceptionOrNull())
    }

    @Test
    fun `invoke should throw RemovingOwnerException when removing the owner`() = runTest {
        val groupId = "1"
        val ownerId = "1"
        val group = createGroupWithMembers(groupId, listOf(ownerId))
        prepareScenario(group)

        val result = removeMemberUseCase.invoke(ownerId, groupId)

        assertIs<GroupException.RemovingOwnerException>(result.exceptionOrNull())
    }

    @Test
    fun `invoke should throw RemovingMemberWithNonZeroBalanceException when member has non-zero balance`() =
        runTest {
            val groupId = UUID.randomUUID().toString()
            val ownerId = UUID.randomUUID().toString()
            val userId = UUID.randomUUID().toString()
            val group = createGroupWithMembersAndBalance(
                groupId = groupId,
                balance = mapOf(ownerId to "0", userId to "10")
            )
            prepareScenario(group)

            val result = removeMemberUseCase.invoke(userId, groupId)

            assertIs<GroupException.RemovingMemberWithNonZeroBalanceException>(result.exceptionOrNull())
        }

    private fun createGroupWithMembers(groupId: String, memberIds: List<String>, ownerId: String = memberIds.first()) =
        Group(groupId, members = memberIds.map { User(it) }, owner = User(ownerId))

    private fun createGroupWithMembersAndBalance(groupId: String, balance: Map<String, String>) =
        createGroupWithMembers(groupId, balance.keys.toList()).copy(balance = balance)

    private fun prepareScenario(group: Group) {
        coEvery { groupRepository.getGroupById(any(), any()) } returns group
        coEvery { groupRepository.removeMember(any(), any()) } returns mockk()
    }
}
