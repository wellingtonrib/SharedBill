package br.com.jwar.sharedbill.data.account.datasources

import br.com.jwar.sharedbill.domain.account.model.User

interface UserDataSource {
    suspend fun getCurrentUser(): User
    suspend fun createUser(userName: String)
    suspend fun saveUser(user: User)
}