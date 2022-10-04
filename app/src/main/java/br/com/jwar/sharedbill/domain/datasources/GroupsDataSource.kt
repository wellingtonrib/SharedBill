package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User

interface GroupsDataSource {
    suspend fun getGroupById(groupId: String): Group
    suspend fun getAllGroups(): List<Group>
    suspend fun createGroup(group: Group): Group
    suspend fun addMember(user: User, group: Group): User
}