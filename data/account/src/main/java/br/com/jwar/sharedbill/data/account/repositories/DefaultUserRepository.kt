package br.com.jwar.sharedbill.data.account.repositories

import br.com.jwar.sharedbill.data.account.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.account.model.User
import br.com.jwar.sharedbill.domain.account.repositories.UserRepository
import javax.inject.Inject

class DefaultUserRepository @Inject  constructor(
    private val userDataSource: UserDataSource,
): UserRepository {

    override suspend fun getCurrentUser() = userDataSource.getCurrentUser()

    override suspend fun saveUser(user: User) = userDataSource.saveUser(user)

    override suspend fun createUser(userName: String) = userDataSource.createUser(userName)
}