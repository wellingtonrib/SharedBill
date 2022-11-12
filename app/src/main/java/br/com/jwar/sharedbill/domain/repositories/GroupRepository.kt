package br.com.jwar.sharedbill.domain.repositories

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun getGroupById(groupId: String, refresh: Boolean = false): Flow<Resource<Group>>
    suspend fun getGroupByIdFlow(groupId: String, refresh: Boolean): Flow<Resource<Group>>
    suspend fun getAllGroups(refresh: Boolean = false): Flow<Resource<List<Group>>>
    suspend fun createGroup(group: Group): Flow<Resource<Group>>
    suspend fun saveGroup(groupId: String, title: String): Flow<Resource<Group>>
    suspend fun addMember(user: User, groupId: String): Flow<Resource<Group>>
    suspend fun removeMember(userId: String, groupId: String): Flow<Resource<Group>>
    suspend fun joinGroup(code: String): Flow<Resource<Group>>
    suspend fun sendPayment(payment: Payment, groupId: String): Flow<Resource<Group>>
}