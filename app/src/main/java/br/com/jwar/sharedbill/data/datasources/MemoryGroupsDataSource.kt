package br.com.jwar.sharedbill.data.datasources

import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result
import javax.inject.Inject

class MemoryGroupsDataSource @Inject constructor(): GroupsDataSource {

    private val cache = mutableSetOf<Group>()

    override suspend fun getGroupById(groupId: String): Result<Group> {
        return cache.firstOrNull { it.id == groupId }?.let { Result.Success(it) }
            ?: Result.Error(GroupException.GroupNotFoundException)
    }

    override suspend fun saveGroup(group: Group): Result<Group> {
        cache.removeIf { it.id == group.id }; cache.add(group)
        return Result.Success(group)
    }
}