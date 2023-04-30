package br.com.jwar.sharedbill.groups.domain.repositories

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun getGroupsStream(): Flow<List<Group>>
    suspend fun getGroupByIdStream(groupId: String): Flow<Group>
    suspend fun getGroupById(groupId: String, refresh: Boolean = false): Group
    suspend fun getGroupByInviteCode(inviteCode: String): Group
    suspend fun createGroup(group: Group): String
    suspend fun updateGroup(groupId: String, title: String)
    suspend fun joinGroup(groupId: String, invitedUser: User, joinedUser: User)
    suspend fun addMember(user: User, groupId: String)
    suspend fun removeMember(user: User, groupId: String)
    suspend fun sendPayment(payment: Payment)
    suspend fun deleteGroup(groupId: String)
}