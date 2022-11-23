package br.com.jwar.sharedbill.data.services

import android.content.Intent
import br.com.jwar.sharedbill.core.di.FirebaseModule.Companion.SIGN_IN_REQUEST
import br.com.jwar.sharedbill.core.di.FirebaseModule.Companion.SIGN_UP_REQUEST
import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.domain.exceptions.AuthException
import br.com.jwar.sharedbill.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Result
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import br.com.jwar.sharedbill.domain.services.AuthService
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private var signInClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
    private var firebaseUserToUserMapper: FirebaseUserToUserMapper,
    private var userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): AuthService {

    override suspend fun signIn(): Result<BeginSignInResult> {
        return withContext(ioDispatcher) {
            try {
                val signInResult = signInClient.beginSignIn(signInRequest).await()
                Result.Success(signInResult)
            } catch (e: Exception) {
                Result.Error(AuthException.SignInException)
            }
        }
    }

    override suspend fun signUp(): Result<BeginSignInResult> {
        return withContext(ioDispatcher) {
            try {
                val signInResult = signInClient.beginSignIn(signUpRequest).await()
                Result.Success(signInResult)
            } catch (e: Exception) {
                Result.Error(AuthException.SignUpException)
            }
        }
    }

    override suspend fun signInFirebase(data: Intent?): Result<Boolean> {
        return withContext(ioDispatcher) {
            try {
                val signInCredential = signInClient.getSignInCredentialFromIntent(data)
                val authCredential = GoogleAuthProvider.getCredential(signInCredential.googleIdToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                val firebaseUser = authResult.user ?: throw UserNotFoundException
                firebaseUserToUserMapper.mapFrom(firebaseUser).run {
                    userRepository.saveUser(this)
                }
                Result.Success(true)
            } catch (e: Exception) {
                Result.Error(AuthException.SignInFirebaseException)
            }
        }
    }

    override suspend fun signOut() =
        withContext(ioDispatcher) {
            signInClient.signOut().await()
            firebaseAuth.signOut()
        }
}