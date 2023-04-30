package br.com.jwar.sharedbill.account.data.datasources

import br.com.jwar.sharedbill.account.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.account.domain.exceptions.UserException
import br.com.jwar.sharedbill.account.domain.model.User
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

    override suspend fun getCurrentUser(): User =
        withContext(ioDispatcher) {
            val firebaseUser = firebaseAuth.currentUser ?: throw UserException.UserNotFoundException
            firebaseUserToUserMapper.mapFrom(firebaseUser)
        }

    override suspend fun createUser(userName: String): Unit =
        withContext(ioDispatcher) {
            val userDoc = firestore.collection(USERS_REF).document()
            val user =
                User(id = userDoc.id, name = userName)
            userDoc.set(user)
        }

    override suspend fun saveUser(user: User): Unit =
        withContext(ioDispatcher) {
            firestore.collection(USERS_REF).document(user.firebaseUserId).set(user).await()
        }
}