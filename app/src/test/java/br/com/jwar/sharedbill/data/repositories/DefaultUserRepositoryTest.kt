package br.com.jwar.sharedbill.data.repositories

import br.com.jwar.sharedbill.CoroutinesTestRule
import br.com.jwar.sharedbill.Fakes
import br.com.jwar.sharedbill.domain.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.exceptions.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultUserRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val userDataSource = mockk<UserDataSource>()
    private val repository = DefaultUserRepository(
        userDataSource = userDataSource,
        ioDispatcher = coroutineRule.dispatcherProvider.io
    )

    @Test
    fun `GIVEN there is a User WHEN getUser RETURN a success resource`() = runTest {
        //GIVEN
        val user = Fakes.user
        coEvery { userDataSource.getUser() } returns user

        //WHEN
        val results = mutableListOf<Resource<User>>()
        repository.getUser().toList(results)

        //THEN
        val success = results.last() as? Resource.Success
        assertNotNull(success)
        assertEquals(user, success?.data)
    }

    @Test
    fun `GIVEN there isn't a User WHEN getUser RETURN a failure`() = runTest {
        //GIVEN
        coEvery { userDataSource.getUser() } throws UserNotFoundException()

        //WHEN
        val results = mutableListOf<Resource<User>>()
        repository.getUser().toList(results)

        //THEN
        val failure = results.last() as? Resource.Failure
        assertNotNull(failure)
        assertTrue(failure?.throwable is UserNotFoundException)
    }
}
