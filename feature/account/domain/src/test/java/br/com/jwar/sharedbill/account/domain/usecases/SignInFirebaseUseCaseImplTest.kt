package br.com.jwar.sharedbill.account.domain.usecases

import android.content.Intent
import br.com.jwar.sharedbill.account.domain.services.AuthService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class SignInFirebaseUseCaseImplTest {

    private val authRepository: AuthService = mockk()

    private val useCase = SignInFirebaseUseCaseImpl(
        authRepository = authRepository
    )

    @Test
    fun `invoke should call authRepository signInFirebase`() = runTest {
        val intent: Intent = mockk()
        coEvery { authRepository.signInFirebase(intent) } returns mockk()

        useCase.invoke(intent)

        coVerify { authRepository.signInFirebase(intent) }
    }
}
