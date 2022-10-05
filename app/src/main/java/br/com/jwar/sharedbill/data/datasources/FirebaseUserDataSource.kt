package br.com.jwar.sharedbill.data.datasources

import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.domain.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.exceptions.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class FirebaseUserDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseUserToUserMapper: FirebaseUserToUserMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): UserDataSource {

    companion object {
        const val USERS_REF = "users"
    }

    override suspend fun getUser(): User =
        withContext(ioDispatcher) {
            val firebaseUser = firebaseAuth.currentUser ?: throw UserNotFoundException()
            return@withContext firebaseUserToUserMapper.mapFrom(firebaseUser)
        }

    override suspend fun saveUser(user: User): Unit =
        withContext(ioDispatcher) {
            firestore.collection(USERS_REF).document(user.firebaseUserId).set(user).await()
        }

    override suspend fun createUser(userName: String): User {
        return withContext(ioDispatcher) {
            val userDoc = firestore.collection(USERS_REF).document()
            val user = User(uid = UUID.randomUUID().toString(), name = userName)
            return@withContext user.also { userDoc.set(it) }
        }
    }
}