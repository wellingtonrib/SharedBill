package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource.*
import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultGroupRepository @Inject constructor(
    private val groupsDataSource: GroupsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupsRepository {

    private val cache = mutableSetOf<Group>()

    override suspend fun getGroupById(groupId: String, refresh: Boolean) = flow {
        emit(Loading)
        try {
            val cached = cache.firstOrNull { it.id == groupId }
            val group = if (cached == null || refresh) {
                groupsDataSource.getGroupById(groupId).also { cache.add(it) }
            } else cached
            emit(Success(group))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun getAllGroups(refresh: Boolean) = flow {
        emit(Loading)
        try {
            val cached = cache.toList()
            val groups = if (cached.isEmpty() || refresh) {
                groupsDataSource.getAllGroups().also { cache.addAll(it) }
            } else cached
            emit(Success(groups))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun createGroup(group: Group) = flow {
        emit(Loading)
        try {
            val newGroup = groupsDataSource.createGroup(group)
            emit(Success(newGroup))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun addMember(userName: String, groupId: String) = flow {
        emit(Loading)
        try {
            val newMember = groupsDataSource.addMember(userName, groupId)
            emit(Success(newMember))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun joinGroup(code: String) = flow {
        emit(Loading)
        try {
            val newMember = groupsDataSource.joinGroup(code)
            emit(Success(newMember))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

}