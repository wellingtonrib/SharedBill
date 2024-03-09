package br.com.jwar.sharedbill.account.data.services

import android.content.Intent
import br.com.jwar.sharedbill.account.data.di.FirebaseModule.Companion.SIGN_IN_REQUEST
import br.com.jwar.sharedbill.account.data.di.FirebaseModule.Companion.SIGN_UP_REQUEST
import br.com.jwar.sharedbill.account.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.account.domain.exceptions.AuthException
import br.com.jwar.sharedbill.account.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.account.domain.services.AuthService
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

@Suppress("LongParameterList")
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
) : AuthService {

    override suspend fun signIn(): BeginSignInResult =
        withContext(ioDispatcher) {
            signInClient.beginSignIn(signInRequest).await() ?: throw AuthException.SignInException
        }

    override suspend fun signUp(): BeginSignInResult =
        withContext(ioDispatcher) {
            signInClient.beginSignIn(signUpRequest).await() ?: throw AuthException.SignUpException
        }

    override suspend fun signInFirebase(data: Intent?) =
        withContext(ioDispatcher) {
            val signInCredential = signInClient.getSignInCredentialFromIntent(data)
            val authCredential = GoogleAuthProvider.getCredential(signInCredential.googleIdToken, null)
            val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                ?: throw AuthException.SignInFirebaseException
            val firebaseUser = authResult.user ?: throw UserNotFoundException
            val domainUser = firebaseUserToUserMapper.mapFrom(firebaseUser)
            userRepository.saveUser(domainUser)
        }

    override suspend fun signOut() =
        withContext(ioDispatcher) {
            signInClient.signOut().await()
            firebaseAuth.signOut()
        }
}
