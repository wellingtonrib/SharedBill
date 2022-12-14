package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.CoroutinesTestRule
import br.com.jwar.sharedbill.Fakes
import br.com.jwar.sharedbill.domain.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.exceptions.UserException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultUserRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val userDataSource = mockk<UserDataSource>()
    private val repository = DefaultUserRepository(
        userDataSource = userDataSource
    )

    @Test
    fun `GIVEN there is a User WHEN getUser RETURN a success resource`() = runTest {
        //GIVEN
        val expected = Fakes.user
        coEvery { userDataSource.getCurrentUser() } returns expected

        //WHEN
        val result = repository.getCurrentUser()

        //THEN
        assertEquals(expected, result)
    }

    @Test(expected = UserException.UserNotFoundException::class)
    fun `GIVEN there isn't a User WHEN getUser RETURN a failure`() = runTest {
        //GIVEN
        coEvery { userDataSource.getCurrentUser() } throws UserException.UserNotFoundException

        repository.getCurrentUser()
    }
}
