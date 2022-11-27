package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.User

interface UserDataSource {
    suspend fun getUser(): Result<User>
    suspend fun createUser(userName: String)
    suspend fun updateUser(user: User)
}