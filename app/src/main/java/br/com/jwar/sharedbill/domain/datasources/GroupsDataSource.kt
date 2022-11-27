package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result

interface GroupsDataSource {
    suspend fun getGroupById(groupId: String): Result<Group>
    suspend fun saveGroup(group: Group): Result<Group>
}