package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID

@ExperimentalCoroutinesApi
class CreateGroupUseCaseImplTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val groupRepository: GroupRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val exceptionHandler: ExceptionHandler = mockk()

    private val createGroupUseCase = CreateGroupUseCaseImpl(
        groupRepository = groupRepository,
        userRepository = userRepository,
        exceptionHandler = exceptionHandler
    )

    @Before
    fun setup() {
        mockkStatic(UUID::class)
    }

    @After
    fun cleanup() {
        unmockkAll()
    }

    @Test
    fun `invoke should create group and return group ID`() = runTest {
        val ownerId = "owner_id"
        val owner = User(ownerId, "firebase_user_id", "John")
        val title = "Group Title"
        val groupId = "group_id"
        val group = Group(
            title = title,
            owner = owner,
            members = listOf(owner),
            firebaseMembersIds = listOf(owner.firebaseUserId)
        )
        every { UUID.randomUUID().toString() } returns ownerId
        coEvery { userRepository.getCurrentUser() } returns owner
        coEvery { groupRepository.createGroup(any()) } returns groupId

        val result = createGroupUseCase.invoke(title)

        coVerify { groupRepository.createGroup(group) }
        assertEquals(Result.success(groupId), result)
    }
}
