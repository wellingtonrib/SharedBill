package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Resource.Failure
import br.com.jwar.sharedbill.domain.model.Resource.Loading
import br.com.jwar.sharedbill.domain.model.Resource.Success
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultGroupRepository @Inject constructor(
    private val groupsDataSource: GroupsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupRepository {

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
            val newGroup = groupsDataSource.createGroup(group).also { cache.add(it) }
            emit(Success(newGroup))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun addMember(user: User, groupId: String) = flow {
        emit(Loading)
        try {
            val groupUpdated = groupsDataSource.addMember(user, groupId).also { cache.add(it) }
            emit(Success(groupUpdated))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun removeMember(userId: String, groupId: String) = flow {
        emit(Loading)
        try {
            val groupUpdated = groupsDataSource.removeMember(userId, groupId).also { cache.add(it) }
            emit(Success(groupUpdated))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun joinGroup(code: String) = flow {
        emit(Loading)
        try {
            val groupUpdated = groupsDataSource.joinGroup(code).also { cache.add(it) }
            emit(Success(groupUpdated))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun sendPayment(payment: Payment, group: Group) = flow {
        emit(Loading)
        try {
            val groupUpdated = groupsDataSource.sendPayment(payment, group).also { cache.add(it) }
            emit(Success(groupUpdated))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun saveGroup(groupId: String, title: String) = flow {
        emit(Loading)
        try {
            val groupUpdated = groupsDataSource.saveGroup(groupId, title).also { cache.add(it) }
            emit(Success(groupUpdated))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)
}