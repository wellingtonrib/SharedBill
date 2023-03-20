package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.account.model.User
import kotlinx.coroutines.flow.Flow

interface GroupsDataSource {
    suspend fun getGroupById(groupId: String, refresh: Boolean): Group
    suspend fun getGroupsStream(): Flow<List<Group>>
    suspend fun getGroupByIdStream(groupId: String): Flow<Group>
    suspend fun getGroupByInviteCode(inviteCode: String): Group
    suspend fun createGroup(group: Group): String
    suspend fun updateGroup(groupId: String, title: String)
    suspend fun addMember(user: User, groupId: String)
    suspend fun removeMember(user: User, groupId: String)
    suspend fun joinGroup(groupId: String, invitedUser: User, joinedUser: User)
    suspend fun sendPayment(payment: Payment)
    suspend fun deleteGroup(groupId: String)
}