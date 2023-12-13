package br.com.jwar.sharedbill.testing.fakes

import br.com.jwar.sharedbill.account.domain.exceptions.UserException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import java.util.UUID
import javax.inject.Inject

class FakeUserRepository @Inject constructor(): UserRepository {

    private var user: User? = null

    override suspend fun getCurrentUser(): User {
        return user ?: throw UserException.UserNotFoundException
    }

    override suspend fun saveUser(user: User) {
        this.user = user
    }

    override suspend fun createUser(userName: String) {
        this.user = User(id = UUID.randomUUID().toString(), name = userName)
    }

}