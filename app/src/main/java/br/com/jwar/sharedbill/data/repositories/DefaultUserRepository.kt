package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.domain.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import javax.inject.Inject

class DefaultUserRepository @Inject  constructor(
    private val userDataSource: UserDataSource,
): UserRepository {

    override suspend fun getCurrentUser() = userDataSource.getCurrentUser()

    override suspend fun saveUser(user: User) = userDataSource.saveUser(user)

    override suspend fun createUser(userName: String) = userDataSource.createUser(userName)
}