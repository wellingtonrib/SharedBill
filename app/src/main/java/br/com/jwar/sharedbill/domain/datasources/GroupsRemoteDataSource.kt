package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GroupsRemoteDataSource: GroupsDataSource {
    override suspend fun getGroupById(groupId: String): Result<Group>
    override suspend fun saveGroup(group: Group): Result<Group>
    suspend fun getGroupsStream(): Flow<Result<List<Group>>>
    suspend fun getGroupByIdStream(groupId: String): Flow<Result<Group>>
    suspend fun getGroupByInviteCode(inviteCode: String): Result<Group>
    suspend fun updateGroup(groupId: String, title: String): Result<Group>
    suspend fun addMember(user: User, groupId: String): Result<Group>
    suspend fun removeMember(user: User, groupId: String): Result<Group>
    suspend fun joinGroup(groupId: String, invitedUser: User, joinedUser: User): Result<Group>
    suspend fun sendPayment(payment: Payment): Result<Group>
}