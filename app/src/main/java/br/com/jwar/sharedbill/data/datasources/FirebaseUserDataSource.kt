package br.com.jwar.sharedbill.data.datasources

import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.domain.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Result
import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseUserDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseUserToUserMapper: FirebaseUserToUserMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): UserDataSource {

    companion object {
        const val USERS_REF = "users"
    }

    override suspend fun getUser(): Result<User> =
        withContext(ioDispatcher) {
            val firebaseUser = firebaseAuth.currentUser ?: return@withContext Result.Error(UserNotFoundException)
            return@withContext Result.Success(firebaseUserToUserMapper.mapFrom(firebaseUser))
        }

    override suspend fun createUser(userName: String): Unit =
        withContext(ioDispatcher) {
            val userDoc = firestore.collection(USERS_REF).document()
            val user = User(id = userDoc.id, name = userName)
            userDoc.set(user)
        }

    override suspend fun updateUser(user: User): Unit =
        withContext(ioDispatcher) {
            firestore.collection(USERS_REF).document(user.firebaseUserId).set(user).await()
        }
}