package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DefaultGroupRepository @Inject constructor(
    private val groupsDataSource: GroupsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupRepository {

    private val cache = mutableSetOf<Group>()

    override suspend fun getGroupsStream(): Flow<List<Group>> =
        groupsDataSource.getGroupsStream()
            .onEach { groups -> groups.forEach { saveGroupInMemoryCache(it) } }
            .flowOn(ioDispatcher)

    override suspend fun getGroupByIdStream(groupId: String) =
        groupsDataSource.getGroupByIdStream(groupId)
            .onEach { saveGroupInMemoryCache(it) }
            .flowOn(ioDispatcher)

    override suspend fun getGroupById(groupId: String, refresh: Boolean): Group {
        val cached = getGroupByIdFromCache(groupId)
        return if (cached == null || refresh) {
            groupsDataSource.getGroupById(groupId).also { saveGroupInMemoryCache(it) }
        } else cached
    }

    override suspend fun getGroupByInviteCode(inviteCode: String) =
        groupsDataSource.getGroupByInviteCode(inviteCode)

    override suspend fun createGroup(group: Group): String =
        groupsDataSource.createGroup(group)

    override suspend fun updateGroup(groupId: String, title: String) =
        groupsDataSource.updateGroup(groupId, title)

    override suspend fun joinGroup(groupId: String, invitedUser: User, joinedUser: User) =
        groupsDataSource.joinGroup(groupId, invitedUser, joinedUser)

    override suspend fun addMember(user: User, groupId: String) =
        groupsDataSource.addMember(user, groupId)

    override suspend fun removeMember(user: User, groupId: String) =
        groupsDataSource.removeMember(user, groupId)

    override suspend fun sendPayment(payment: Payment) =
        groupsDataSource.sendPayment(payment)

    override suspend fun deleteGroup(groupId: String) =
        groupsDataSource.deleteGroup(groupId).also { removeGroupFromMemoryCache(groupId) }

    private fun saveGroupInMemoryCache(group: Group) = with(ioDispatcher) {
        cache.removeIf { it.id == group.id }; cache.add(group)
    }

    private fun removeGroupFromMemoryCache(groupId: String) = with(ioDispatcher) {
        cache.removeIf { it.id == groupId }
    }

    private fun getGroupByIdFromCache(groupId: String) = cache.firstOrNull { it.id == groupId }
}