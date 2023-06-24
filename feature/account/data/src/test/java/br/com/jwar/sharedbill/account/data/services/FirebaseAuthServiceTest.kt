package br.com.jwar.sharedbill.account.data.services

import android.content.Intent
import br.com.jwar.sharedbill.account.data.di.FirebaseModule.Companion.SIGN_IN_REQUEST
import br.com.jwar.sharedbill.account.data.di.FirebaseModule.Companion.SIGN_UP_REQUEST
import br.com.jwar.sharedbill.account.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID
import javax.inject.Named
import kotlin.test.assertEquals

internal class FirebaseAuthServiceTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val firebaseAuth: FirebaseAuth = mockk()
    private val signInClient: SignInClient = mockk()
    @Named(SIGN_IN_REQUEST)
    private val signInRequest: BeginSignInRequest = mockk()
    @Named(SIGN_UP_REQUEST)
    private val signUpRequest: BeginSignInRequest = mockk()
    private val firebaseUserToUserMapper: FirebaseUserToUserMapper = mockk()
    private val userRepository: UserRepository = mockk()

    private val authService = FirebaseAuthService(
        firebaseAuth = firebaseAuth,
        signInClient = signInClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
        firebaseUserToUserMapper = firebaseUserToUserMapper,
        userRepository = userRepository,
        ioDispatcher = coroutineRule.dispatcher
    )

    @Test
    fun `signIn should call beginSignIn and return result`() = runTest {
        val expectedResult = mockk<BeginSignInResult>()
        prepareScenario(beginSignInResult = expectedResult,)

        val signInResult = authService.signIn()

        coVerify { signInClient.beginSignIn(signInRequest) }
        assertEquals(expectedResult, signInResult)
    }

    @Test
    fun `signUp should call beginSignIn and return result`() = runTest {
        val expectedResult = mockk<BeginSignInResult>()
        prepareScenario(beginSignInResult = expectedResult,)

        val signUpResult = authService.signUp()

        coVerify { signInClient.beginSignIn(signUpRequest) }
        assertEquals(expectedResult, signUpResult)
    }

    @Test
    fun `signInFirebase should sign in with Firebase and save user`() = runTest {
        val signInFirebaseIntent: Intent = mockk()
        val googleAuthCredential: AuthCredential = mockk()
        val domainUser: User = mockk()
        prepareScenario(
            signInFirebaseIntent = signInFirebaseIntent,
            googleAuthCredential = googleAuthCredential,
            domainUser = domainUser
        )

        authService.signInFirebase(signInFirebaseIntent)

        verify { signInClient.getSignInCredentialFromIntent(signInFirebaseIntent) }
        verify { firebaseAuth.signInWithCredential(googleAuthCredential) }
        coVerify { userRepository.saveUser(domainUser) }
    }

    @Test
    fun `signOut should sign out from SignInClient and FirebaseAuth`() = runTest {
        prepareScenario()

        authService.signOut()

        coVerify { signInClient.signOut() }
        verify { firebaseAuth.signOut() }
    }

    private fun prepareScenario(
        beginSignInResult: BeginSignInResult = mockk(),
        beginSignInResultTask: Task<BeginSignInResult> = Tasks.forResult(beginSignInResult),
        signInCredential: SignInCredential = mockk(),
        signInFirebaseIntent: Intent? = mockk(),
        googleIdToken: String? = UUID.randomUUID().toString(),
        authResult: AuthResult = mockk(),
        googleAuthCredential: AuthCredential = mockk(),
        authResultTask: Task<AuthResult> = Tasks.forResult(authResult),
        firebaseUser: FirebaseUser = mockk(),
        domainUser: User = mockk(),
        signOutTask: Task<Void> = mockk()
    ) {
        mockkStatic(GoogleAuthProvider::class)
        coEvery { signInClient.beginSignIn(signInRequest) } returns beginSignInResultTask
        coEvery { signInClient.beginSignIn(signUpRequest) } returns beginSignInResultTask
        every { signInClient.getSignInCredentialFromIntent(signInFirebaseIntent) } returns signInCredential
        every { signInCredential.googleIdToken } returns googleIdToken
        every { GoogleAuthProvider.getCredential(googleIdToken, null) } returns googleAuthCredential
        coEvery { firebaseAuth.signInWithCredential(googleAuthCredential) } returns authResultTask
        every { authResult.user } returns firebaseUser
        every { firebaseUserToUserMapper.mapFrom(firebaseUser) } returns domainUser
        coEvery { userRepository.saveUser(domainUser) } just Runs
        coEvery { signInClient.signOut() } returns signOutTask
        coEvery { firebaseAuth.signOut() } just Runs
        every { signOutTask.isComplete } returns true
        every { signOutTask.exception } returns null
        every { signOutTask.isCanceled } returns false
        every { signOutTask.result } returns mockk()
    }
}