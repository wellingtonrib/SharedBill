package br.com.jwar.sharedbill.domain.account.repositories

import br.com.jwar.sharedbill.domain.account.model.User

interface UserRepository {
    suspend fun getCurrentUser(): User
    suspend fun saveUser(user: User)
    suspend fun createUser(userName: String)
}