package br.com.jwar.sharedbill.domain.repositories

import br.com.jwar.sharedbill.domain.model.User

interface UserRepository {
    suspend fun getUser(): Result<User>
    suspend fun saveUser(user: User)
    suspend fun createUser(userName: String)
}