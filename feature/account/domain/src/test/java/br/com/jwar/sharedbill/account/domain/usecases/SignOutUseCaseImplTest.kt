package br.com.jwar.sharedbill.account.domain.usecases

import br.com.jwar.sharedbill.account.domain.services.AuthService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class SignOutUseCaseImplTest {

    private val authRepository: AuthService = mockk()

    private val useCase = SignOutUseCaseImpl(
        authRepository = authRepository
    )

    @Test
    fun `invoke should call authRepository signOut`() = runTest {
        coEvery { authRepository.signOut() } returns mockk()

        useCase.invoke()

        coVerify { authRepository.signOut() }
    }
}