package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetUserUseCaseImplTest {

    private val userRepository = mockk<UserRepository>()
    private val useCase = GetUserUseCaseImpl(
        userRepository = userRepository
    )

    @Test
    fun `GIVEN GetUserUseCase WHEN invoked SHOULD call getUser from repository`() = runTest {
        //GIVEN
        coEvery { userRepository.getUser() } returns mockk()
        //WHEN
        useCase.invoke()
        //THEN
        coVerify { userRepository.getUser() }
    }
}