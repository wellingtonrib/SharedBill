package br.com.jwar.sharedbill.account.domain.usecases

import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetCurrentUserUseCaseImplTest {

    private val userRepository = mockk<UserRepository>()

    private val useCase = GetCurrentUserUseCaseImpl(
        userRepository = userRepository
    )

    @Test
    fun `invoke should call userRepository getCurrentUser`() = runTest {
        coEvery { userRepository.getCurrentUser() } returns mockk()

        useCase.invoke()

        coVerify { userRepository.getCurrentUser() }
    }
}
