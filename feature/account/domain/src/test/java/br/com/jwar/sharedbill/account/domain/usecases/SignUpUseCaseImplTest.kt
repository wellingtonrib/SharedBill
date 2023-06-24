package br.com.jwar.sharedbill.account.domain.usecases

import br.com.jwar.sharedbill.account.domain.services.AuthService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class SignUpUseCaseImplTest {

    private val authRepository: AuthService = mockk()

    private val useCase = SignUpUseCaseImpl(
        authRepository = authRepository
    )

    @Test
    fun `invoke should call authRepository signUp`() = runTest {
        coEvery { authRepository.signUp() } returns mockk()

        useCase.invoke()

        coVerify { authRepository.signUp() }
    }
}