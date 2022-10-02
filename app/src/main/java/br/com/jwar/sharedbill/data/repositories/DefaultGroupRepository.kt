package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.exceptions.GroupNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.Resource.*
import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DefaultGroupRepository @Inject constructor(
    private val groupsDataSource: GroupsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupsRepository {

    private val cache = mutableSetOf<Group>()

    override suspend fun getGroupById(groupId: String, refresh: Boolean): Flow<Resource<Group>> = flow {
        emit(Loading)
        try {
            val group = if (cache.isEmpty() || refresh) {
                groupsDataSource.getGroupById(groupId).also { cache.add(it) }
            } else cache.firstOrNull { it.id == groupId } ?: throw GroupNotFoundException()
            emit(Success(group))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun getAllGroups(refresh: Boolean): Flow<Resource<List<Group>>> = flow {
        emit(Loading)
        try {
            val groups = if (cache.isEmpty() || refresh) {
                groupsDataSource.getAllGroups().also { cache.addAll(it) }
            } else cache.toList()
            emit(Success(groups))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun createGroup(group: Group): Flow<Resource<Group>> = flow {
        emit(Loading)
        try {
            val newGroup = groupsDataSource.createGroup(group)
            emit(Success(newGroup))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

}