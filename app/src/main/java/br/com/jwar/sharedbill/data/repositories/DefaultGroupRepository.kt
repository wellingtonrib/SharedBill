package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.datasources.GroupsRemoteDataSource
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.model.Result
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class DefaultGroupRepository @Inject constructor(
    private val groupsRemoteDataSource: GroupsRemoteDataSource,
    private val groupsMemoryDataSource: GroupsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupRepository {

    override suspend fun getGroupsStream() =
        groupsRemoteDataSource.getGroupsStream()
            .onEach { result ->
                result.getOrNull()?.let {
                    groups -> groups.forEach { saveGroupInMemoryCache(it) }
                }
            }
            .flowOn(ioDispatcher)

    override suspend fun getGroupByIdStream(groupId: String) =
        groupsRemoteDataSource.getGroupByIdStream(groupId)
            .onEach { result ->
                result.getOrNull()?.let {
                    saveGroupInMemoryCache(it)
                }
            }
            .flowOn(ioDispatcher)

    override suspend fun getGroupById(groupId: String, refresh: Boolean): Result<Group> {
        val cached = groupsMemoryDataSource.getGroupById(groupId).getOrNull()
        return if (cached == null || refresh) {
            groupsRemoteDataSource.getGroupById(groupId).onSuccess { saveGroupInMemoryCache(it) }
        } else Result.Success(cached)
    }

    override suspend fun getGroupByInviteCode(inviteCode: String) =
        groupsRemoteDataSource.getGroupByInviteCode(inviteCode)

    override suspend fun createGroup(group: Group) =
        groupsRemoteDataSource.saveGroup(group)
            .onSuccess { saveGroupInMemoryCache(it) }

    override suspend fun updateGroup(groupId: String, title: String) =
        groupsRemoteDataSource.updateGroup(groupId, title)
            .onSuccess { saveGroupInMemoryCache(it) }

    override suspend fun joinGroup(groupId: String, invitedUser: User, joinedUser: User) =
        groupsRemoteDataSource.joinGroup(groupId, invitedUser, joinedUser)
            .onSuccess { saveGroupInMemoryCache(it) }

    override suspend fun addMember(user: User, groupId: String) =
        groupsRemoteDataSource.addMember(user, groupId)
            .onSuccess { saveGroupInMemoryCache(it) }

    override suspend fun removeMember(user: User, groupId: String) =
        groupsRemoteDataSource.removeMember(user, groupId)
            .onSuccess { saveGroupInMemoryCache(it) }

    override suspend fun sendPayment(payment: Payment) =
        groupsRemoteDataSource.sendPayment(payment)
            .onSuccess { saveGroupInMemoryCache(it) }

    private suspend fun saveGroupInMemoryCache(group: Group) =
        groupsMemoryDataSource.saveGroup(group)
}