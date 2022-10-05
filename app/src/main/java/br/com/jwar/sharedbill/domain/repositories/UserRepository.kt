package br.com.jwar.sharedbill.domain.repositories

import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): Flow<Resource<User>>
    suspend fun createUser(userName: String): Flow<Resource<User>>
}