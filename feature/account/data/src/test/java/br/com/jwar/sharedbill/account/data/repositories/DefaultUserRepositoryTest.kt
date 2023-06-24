package br.com.jwar.sharedbill.account.data.repositories

import br.com.jwar.sharedbill.account.data.datasources.UserDataSource
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import br.com.jwar.sharedbill.testing.Fakes
import io.mockk.coEvery
import io.mockk.coVerify
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
    fun `getCurrentUser should call userDataSource getCurrentUser and return it`() = runTest {
        val currentUser = Fakes.makeUser()
        prepareScenario(currentUser = currentUser)

        val result = repository.getCurrentUser()

        coVerify { userDataSource.getCurrentUser() }
        assertEquals(currentUser, result)
    }

    @Test
    fun `saveUser should call userDataSource saveUser`() = runTest {
        val user = Fakes.makeUser()
        prepareScenario()

        repository.saveUser(user)

        coVerify { userDataSource.saveUser(user) }
    }

    @Test
    fun `createUser should call userDataSource createUser`() = runTest {
        val userName = "User name"
        prepareScenario()

        repository.createUser(userName)

        coVerify { userDataSource.createUser(userName) }
    }

    private fun prepareScenario(
        currentUser: User = Fakes.makeUser()
    ) {
        coEvery { userDataSource.getCurrentUser() } returns currentUser
        coEvery { userDataSource.saveUser(any()) } returns mockk()
        coEvery { userDataSource.createUser(any()) } returns mockk()
    }
}
