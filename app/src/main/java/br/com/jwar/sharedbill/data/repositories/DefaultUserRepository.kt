package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.domain.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.Resource.Failure
import br.com.jwar.sharedbill.domain.model.Resource.Loading
import br.com.jwar.sharedbill.domain.model.Resource.Success
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultUserRepository @Inject constructor(
    private val userDataSource: UserDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): UserRepository {

    override suspend fun getUser(): Flow<Resource<User>> = flow {
        emit(Loading)
        try {
            val user = userDataSource.getUser()
            emit(Success(user))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)

    override suspend fun createUser(user: User): Flow<Resource<User>> = flow {
        emit(Loading)
        try {
            val savedUser = userDataSource.createUser(user)
            emit(Success(savedUser))
        } catch (exception: Exception) {
            emit(Failure(exception))
        }
    }.flowOn(ioDispatcher)
}