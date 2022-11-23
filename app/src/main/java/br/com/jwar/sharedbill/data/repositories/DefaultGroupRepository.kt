package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.model.*
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class DefaultGroupRepository @Inject constructor(
    private val groupsDataSource: GroupsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupRepository {

    private val cache = mutableSetOf<Group>()

    override suspend fun getGroupsStream() =
        groupsDataSource.getGroupsStream()
            .onEach { result -> if (result is Result.Success) result.data.forEach { setGroupCache(it) } }
            .flowOn(ioDispatcher)

    override suspend fun getGroupById(groupId: String, refresh: Boolean): Result<Group> {
        val cached = getGroupFromCache(groupId)
        return if (cached == null || refresh) {
            groupsDataSource.getGroupById(groupId)
                .onSuccess { setGroupCache(it) }
        } else Result.Success(cached)
    }

    override suspend fun getGroupByInviteCode(inviteCode: String) =
        groupsDataSource.getGroupByInviteCode(inviteCode)

    override suspend fun createGroup(group: Group) =
        groupsDataSource.createGroup(group)
            .onSuccess { setGroupCache(it) }

    override suspend fun updateGroup(groupId: String, title: String) =
        groupsDataSource.updateGroup(groupId, title)
            .onSuccess { setGroupCache(it) }

    override suspend fun joinGroup(groupId: String, invitedUser: User, joinedUser: User) =
        groupsDataSource.joinGroup(groupId, invitedUser, joinedUser)
            .onSuccess { setGroupCache(it) }

    override suspend fun addMember(user: User, groupId: String) =
        groupsDataSource.addMember(user, groupId)
            .onSuccess { setGroupCache(it) }

    override suspend fun removeMember(user: User, groupId: String) =
        groupsDataSource.removeMember(user, groupId)
            .onSuccess { setGroupCache(it) }

    override suspend fun sendPayment(payment: Payment) =
        groupsDataSource.sendPayment(payment)
            .onSuccess { setGroupCache(it) }

    private fun getGroupFromCache(groupId: String) =
        cache.firstOrNull { it.id == groupId }

    private fun setGroupCache(group: Group) {
        getGroupFromCache(group.id)?.let { cache.remove(it) }
        cache.add(group)
    }
}