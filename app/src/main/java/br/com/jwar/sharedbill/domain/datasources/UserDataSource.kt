package br.com.jwar.sharedbill.domain.datasources

import br.com.jwar.sharedbill.domain.model.User

interface UserDataSource {
    suspend fun getUser(): User
    suspend fun saveUser(user: User)
}