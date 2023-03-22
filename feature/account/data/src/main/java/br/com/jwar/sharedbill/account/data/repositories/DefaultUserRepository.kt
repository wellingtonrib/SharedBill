package br.com.jwar.sharedbill.account.data.repositories

import br.com.jwar.sharedbill.account.data.datasources.UserDataSource
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import javax.inject.Inject

class DefaultUserRepository @Inject  constructor(
    private val userDataSource: UserDataSource,
): br.com.jwar.sharedbill.account.domain.repositories.UserRepository {

    override suspend fun getCurrentUser() = userDataSource.getCurrentUser()

    override suspend fun saveUser(user: br.com.jwar.sharedbill.account.domain.model.User) = userDataSource.saveUser(user)

    override suspend fun createUser(userName: String) = userDataSource.createUser(userName)
}