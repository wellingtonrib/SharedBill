package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.domain.model.Group
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
class JoinGroupUseCaseImplTest {

    private val groupRepository: GroupRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val exceptionHandler: ExceptionHandler = mockk(relaxed = true)
    private val joinGroupUseCase = JoinGroupUseCaseImpl(groupRepository, userRepository, exceptionHandler)

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Test
    fun `invoke should join group and return group ID when invite code is valid`() = runTest {
        val invitedCode = "inviteCode123"
        val invitedUser = User("1", "Invited User", inviteCode = invitedCode)
        val currentUser = User("2", "Current User")
        val group = Group(id = "1", members = listOf(invitedUser))
        val joinedUser = invitedUser.copy(
            firebaseUserId = currentUser.firebaseUserId,
            inviteCode = "",
            photoUrl = currentUser.photoUrl.toString(),
            email = currentUser.email
        )
        prepareScenario(group = group, currentUser = currentUser)

        val result = joinGroupUseCase.invoke(invitedCode)

        coVerify { groupRepository.joinGroup(group.id, invitedCode, joinedUser) }
        assertEquals(Result.success(group.id), result)
    }

    @Test
    fun `invoke should throw InvalidInviteCodeException when invite code is invalid`() = runTest {
        val invitedCode = "inviteCode123"
        val group = Group("1", "Group 1")
        val currentUser = User("2", "Current User")
        prepareScenario(group = group, currentUser = currentUser)

        val result = joinGroupUseCase.invoke(invitedCode)

        assertIs<GroupException.InvalidInviteCodeException>(result.exceptionOrNull())
    }

    private fun prepareScenario(
        group: Group,
        currentUser: User,
    ) {
        coEvery { groupRepository.getGroupByInviteCode(any()) } returns group
        coEvery { userRepository.getCurrentUser() } returns currentUser
        coEvery { groupRepository.joinGroup(any(), any(), any()) } returns mockk()
    }
}
