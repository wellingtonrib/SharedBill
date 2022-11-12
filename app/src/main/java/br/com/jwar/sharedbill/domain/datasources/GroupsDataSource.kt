package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GroupsDataSource {
    fun getGroupByIdFlow(groupId: String): Flow<Resource<Group>>
    suspend fun getGroupById(groupId: String): Group
    suspend fun getAllGroups(): List<Group>
    suspend fun createGroup(group: Group): Group
    suspend fun saveGroup(groupId: String, title: String): Group
    suspend fun addMember(user: User, groupId: String): Group
    suspend fun removeMember(userId: String, groupId: String): Group
    suspend fun joinGroup(code: String): Group
    suspend fun sendPayment(payment: Payment, groupId: String): Group
}