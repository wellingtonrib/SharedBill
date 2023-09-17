package br.com.jwar.sharedbill.groups.data.repositories

import br.com.jwar.sharedbill.groups.data.datasources.GroupsDataSource
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultGroupRepository @Inject constructor(
    private val groupsDataSource: GroupsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupRepository {

    override suspend fun getGroupsStream(): Flow<List<Group>> =
        groupsDataSource.getGroupsStream().flowOn(ioDispatcher)

    override suspend fun getGroupByIdStream(groupId: String) =
        groupsDataSource.getGroupByIdStream(groupId).flowOn(ioDispatcher)

    override suspend fun getGroupById(groupId: String, refresh: Boolean): Group =
        groupsDataSource.getGroupById(groupId, refresh)

    override suspend fun getGroupByInviteCode(inviteCode: String) =
        groupsDataSource.getGroupByInviteCode(inviteCode)

    override suspend fun createGroup(group: Group): String =
        groupsDataSource.createGroup(group)

    override suspend fun updateGroup(groupId: String, title: String) =
        groupsDataSource.updateGroup(groupId, title)

    override suspend fun joinGroup(groupId: String, inviteCode: String, joinedUser: User) =
        groupsDataSource.joinGroup(groupId, inviteCode, joinedUser)

    override suspend fun addMember(user: User, groupId: String) =
        groupsDataSource.addMember(user, groupId)

    override suspend fun removeMember(user: User, groupId: String) =
        groupsDataSource.removeMember(user, groupId)

    override suspend fun sendPayment(payment: Payment) =
        groupsDataSource.sendPayment(payment)

    override suspend fun deleteGroup(groupId: String) =
        groupsDataSource.deleteGroup(groupId)
}