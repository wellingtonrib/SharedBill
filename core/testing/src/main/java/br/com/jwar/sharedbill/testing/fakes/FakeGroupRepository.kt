package br.com.jwar.sharedbill.testing.fakes

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeGroupRepository @Inject constructor(): GroupRepository {

    private val groups = mutableListOf<Group>()

    override suspend fun getGroupsStream(): Flow<List<Group>> {
        return flowOf(groups)
    }

    override suspend fun getGroupByIdStream(groupId: String): Flow<Group> {
        return flowOf(getGroupById(groupId))
    }

    override suspend fun getGroupById(groupId: String, refresh: Boolean): Group {
        return groups.firstOrNull { it.id == groupId } ?: throw GroupException.GroupNotFoundException
    }

    override suspend fun getGroupByInviteCode(inviteCode: String): Group {
        TODO("Not yet implemented")
    }

    override suspend fun createGroup(group: Group): String {
        groups.add(group)
        return group.id
    }

    override suspend fun updateGroup(groupId: String, title: String) {
        TODO("Not yet implemented")
    }

    override suspend fun joinGroup(groupId: String, inviteCode: String, joinedUser: User) {
        TODO("Not yet implemented")
    }

    override suspend fun addMember(user: User, groupId: String) {
        val group = getGroupById(groupId)
        groups[groups.indexOf(group)] = group.copy(members = group.members + user)
    }

    override suspend fun removeMember(user: User, groupId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendPayment(payment: Payment) {
        val group = getGroupById(payment.groupId)
        groups[groups.indexOf(group)] = group.copy(payments = group.payments + payment)
    }

    override suspend fun deleteGroup(groupId: String) {
        TODO("Not yet implemented")
    }

}