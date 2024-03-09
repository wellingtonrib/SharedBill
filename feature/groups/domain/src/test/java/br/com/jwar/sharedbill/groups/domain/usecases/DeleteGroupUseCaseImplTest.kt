package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
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
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
class DeleteGroupUseCaseImplTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val groupRepository: GroupRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val deleteGroupUseCase = DeleteGroupUseCaseImpl(groupRepository, userRepository)

    @Test
    fun `invoke should delete group when called by the owner and return success result`() = runTest {
        val groupId = "group_id"
        val currentUser = User(firebaseUserId = "firebase_user_id")
        val group = Group(groupId, owner = currentUser)
        prepareScenario(group, currentUser)

        val result = deleteGroupUseCase.invoke(groupId)

        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun `invoke should throw exception when called by non-owner`() = runTest {
        val groupId = "group_id"
        val currentUser = User(firebaseUserId = "firebase_user_id")
        val group = Group(groupId, owner = User(firebaseUserId = "other_user_id"))
        prepareScenario(group, currentUser)

        val result = deleteGroupUseCase.invoke(groupId)

        assertIs<GroupException.DeletingFromNonOwnerException>(result.exceptionOrNull())
    }

    private fun prepareScenario(group: Group, currentUser: User) {
        coEvery { groupRepository.getGroupById(any()) } returns group
        coEvery { userRepository.getCurrentUser() } returns currentUser
        coEvery { groupRepository.deleteGroup(any()) } returns Unit
    }
}
