package br.com.jwar.sharedbill.data.services

import android.content.Intent
import br.com.jwar.sharedbill.core.di.FirebaseModule.Companion.SIGN_IN_REQUEST
import br.com.jwar.sharedbill.core.di.FirebaseModule.Companion.SIGN_UP_REQUEST
import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.domain.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.exceptions.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Resource.Failure
import br.com.jwar.sharedbill.domain.model.Resource.Loading
import br.com.jwar.sharedbill.domain.model.Resource.Success
import br.com.jwar.sharedbill.domain.services.AuthService
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private var signInClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
    private var firebaseUserToUserMapper: FirebaseUserToUserMapper,
    private var userDataSource: UserDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): AuthService {

    override suspend fun signIn() = flow {
        emit(Loading)
        try {
            val result = signInClient.beginSignIn(signInRequest).await()
            emit(Success(result))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }.flowOn(ioDispatcher)

    override suspend fun signUp() = flow {
        emit(Loading)
        try {
            val result = signInClient.beginSignIn(signUpRequest).await()
            emit(Success(result))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }.flowOn(ioDispatcher)

    override suspend fun signInFirebase(data: Intent?) = flow {
        emit(Loading)
        try {
            val signInCredential = signInClient.getSignInCredentialFromIntent(data)
            val authCredential = GoogleAuthProvider.getCredential(signInCredential.googleIdToken, null)
            val authResult = firebaseAuth.signInWithCredential(authCredential).await()
            val firebaseUser = authResult.user ?: throw UserNotFoundException()

            firebaseUserToUserMapper.mapFrom(firebaseUser).run {
                userDataSource.saveUser(this)
            }
            emit(Success(true))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }.flowOn(ioDispatcher)

    override suspend fun signOut() = flow {
        emit(Loading)
        try {
            signInClient.signOut().await()
            firebaseAuth.signOut()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }.flowOn(ioDispatcher)
}