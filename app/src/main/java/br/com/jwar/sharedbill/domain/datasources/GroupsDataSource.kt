package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.Group

interface GroupsDataSource {
    suspend fun getGroupById(groupId: String): Result<Group>
    suspend fun saveGroup(group: Group): Result<Group>
}