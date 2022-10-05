package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.Group

interface GroupsDataSource {
    suspend fun getGroupById(groupId: String): Group
    suspend fun getAllGroups(): List<Group>
    suspend fun createGroup(group: Group): Group
    suspend fun addMember(userName: String, groupId: String): Group
    suspend fun removeMember(userId: String, groupId: String): Group
    suspend fun joinGroup(code: String): Group
}