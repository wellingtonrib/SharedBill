package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.User

interface UserDataSource {
    suspend fun getCurrentUser(): User
    suspend fun createUser(userName: String)
    suspend fun saveUser(user: User)
}