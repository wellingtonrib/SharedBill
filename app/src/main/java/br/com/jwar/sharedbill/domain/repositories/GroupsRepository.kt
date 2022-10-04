package br.com.jwar.sharedbill.domain.repositories

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GroupsRepository {
    suspend fun getGroupById(groupId: String, refresh: Boolean = false): Flow<Resource<Group>>
    suspend fun getAllGroups(refresh: Boolean = false): Flow<Resource<List<Group>>>
    suspend fun createGroup(group: Group): Flow<Resource<Group>>
    suspend fun addMember(user: User, group: Group): Flow<Resource<User>>
}