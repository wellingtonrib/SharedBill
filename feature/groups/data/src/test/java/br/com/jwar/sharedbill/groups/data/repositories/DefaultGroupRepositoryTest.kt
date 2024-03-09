package br.com.jwar.sharedbill.groups.data.repositories

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.data.datasources.GroupsDataSource
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID

internal class DefaultGroupRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val groupsDataSource: GroupsDataSource = mockk(relaxed = true)

    private val defaultGroupRepository = DefaultGroupRepository(
        groupsDataSource = groupsDataSource,
        ioDispatcher = coroutineRule.dispatcher
    )

    @Test
    fun `getGroupsStream should call groupsDataSource and return flow`() = runTest {
        val groups = listOf(Group("1", "Group 1"), Group("2", "Group 2"))
        prepareScenario(groupsStreamResult = flowOf(groups),)

        val result = defaultGroupRepository.getGroupsStream()

        assert(result.first() == groups)
        coVerify { groupsDataSource.getGroupsStream() }
    }

    @Test
    fun `getGroupByIdStream should call groupsDataSource and return flow`() = runTest {
        val groupId = "1"
        val group = Group(groupId, "Group 1")
        prepareScenario(groupByIdStreamResult = flowOf(group),)

        val result = defaultGroupRepository.getGroupByIdStream(groupId)

        assert(result.first() == group)
        coVerify { groupsDataSource.getGroupByIdStream(groupId) }
    }

    @Test
    fun `getGroupById should call groupsDataSource and return group`() = runTest {
        val groupId = "1"
        val group = Group(groupId, "Group 1")
        prepareScenario(groupByIdResult = group,)

        val result = defaultGroupRepository.getGroupById(groupId, refresh = false)

        assert(result == group)
        coVerify { groupsDataSource.getGroupById(groupId, false) }
    }

    @Test
    fun `getGroupByInviteCode should call groupsDataSource and return group`() = runTest {
        val inviteCode = "ABC123"
        val group = Group("1", "Group 1")
        prepareScenario(groupByInviteCodeResult = group,)

        val result = defaultGroupRepository.getGroupByInviteCode(inviteCode)

        assert(result == group)
        coVerify { groupsDataSource.getGroupByInviteCode(inviteCode) }
    }

    @Test
    fun `createGroup should call groupsDataSource and return groupId`() = runTest {
        val group = Group("1", "Group 1")
        prepareScenario(createGroupResult = "1",)

        val result = defaultGroupRepository.createGroup(group)

        assert(result == "1")
        coVerify { groupsDataSource.createGroup(group) }
    }

    @Test
    fun `updateGroup should call groupsDataSource`() = runTest {
        val groupId = "1"
        val title = "New Title"
        prepareScenario()

        defaultGroupRepository.updateGroup(groupId, title)

        coVerify { groupsDataSource.updateGroup(groupId, title) }
    }

    @Test
    fun `joinGroup should call groupsDataSource`() = runTest {
        val groupId = "1"
        val inviteCode = "123"
        val joinedUser = User("joined_user_id", "Joined User")
        prepareScenario()

        defaultGroupRepository.joinGroup(groupId, inviteCode, joinedUser)

        coVerify { groupsDataSource.joinGroup(groupId, inviteCode, joinedUser) }
    }

    @Test
    fun `addMember should call groupsDataSource`() = runTest {
        val user = User("user_id", "User")
        val groupId = "1"
        prepareScenario()

        defaultGroupRepository.addMember(user, groupId)

        coVerify { groupsDataSource.addMember(user, groupId) }
    }

    @Test
    fun `removeMember should call groupsDataSource`() = runTest {
        val user = User("user_id", "User")
        val groupId = "1"
        prepareScenario()

        defaultGroupRepository.removeMember(user, groupId)

        coVerify { groupsDataSource.removeMember(user, groupId) }
    }

    @Test
    fun `sendPayment should call groupsDataSource`() = runTest {
        val payment = Payment("payment_id", "Payment", "", "100.0")
        prepareScenario()

        defaultGroupRepository.sendPayment(payment)

        coVerify { groupsDataSource.sendPayment(payment) }
    }

    @Test
    fun `deleteGroup should call groupsDataSource`() = runTest {
        val groupId = "1"
        prepareScenario()

        defaultGroupRepository.deleteGroup(groupId)

        coVerify { groupsDataSource.deleteGroup(groupId) }
    }

    @Suppress("LongParameterList")
    private fun prepareScenario(
        groupsStreamResult: Flow<List<Group>> = flowOf(),
        groupByIdStreamResult: Flow<Group> = flowOf(),
        groupByIdResult: Group = mockk(),
        groupByInviteCodeResult: Group = mockk(),
        createGroupResult: String = UUID.randomUUID().toString(),
        updateGroupResult: Unit = mockk(),
        joinGroupResult: Unit = mockk(),
        addMemberResult: Unit = mockk(),
        removeMemberResult: Unit = mockk(),
        sendPaymentResult: Unit = mockk(),
        deleteGroupResult: Unit = mockk(),
    ) {
        coEvery { groupsDataSource.getGroupsStream() } returns groupsStreamResult
        coEvery { groupsDataSource.getGroupByIdStream(any()) } returns groupByIdStreamResult
        coEvery { groupsDataSource.getGroupById(any(), any()) } returns groupByIdResult
        coEvery { groupsDataSource.getGroupByInviteCode(any()) } returns groupByInviteCodeResult
        coEvery { groupsDataSource.createGroup(any()) } returns createGroupResult
        coEvery { groupsDataSource.updateGroup(any(), any()) } returns updateGroupResult
        coEvery { groupsDataSource.joinGroup(any(), any(), any()) } returns joinGroupResult
        coEvery { groupsDataSource.addMember(any(), any()) } returns addMemberResult
        coEvery { groupsDataSource.removeMember(any(), any()) } returns removeMemberResult
        coEvery { groupsDataSource.sendPayment(any()) } returns sendPaymentResult
        coEvery { groupsDataSource.deleteGroup(any()) } returns deleteGroupResult
    }
}
