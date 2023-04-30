package br.com.jwar.sharedbill.account.data.datasources

import br.com.jwar.sharedbill.account.domain.model.User

interface UserDataSource {
    suspend fun getCurrentUser(): User
    suspend fun createUser(userName: String)
    suspend fun saveUser(user: User)
}