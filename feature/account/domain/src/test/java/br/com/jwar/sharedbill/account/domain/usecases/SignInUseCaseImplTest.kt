package br.com.jwar.sharedbill.account.domain.usecases

import br.com.jwar.sharedbill.account.domain.services.AuthService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class SignInUseCaseImplTest {

    private val authRepository: AuthService = mockk()

    private val useCase = SignInUseCaseImpl(
        authRepository = authRepository
    )

    @Test
    fun `invoke should call authRepository signIn`() = runTest {
        coEvery { authRepository.signIn() } returns mockk()

        useCase.invoke()

        coVerify { authRepository.signIn() }
    }
}
