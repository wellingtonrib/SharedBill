package br.com.jwar.sharedbill.account.data.datasources

import br.com.jwar.sharedbill.account.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.account.domain.exceptions.UserException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class FirebaseUserDataSourceTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()


    private val firebaseUserToUserMapper = mockk<FirebaseUserToUserMapper>()
    private val firebaseAuth = mockk<FirebaseAuth>()
    private val firestore = mockk<FirebaseFirestore>()
    private val firebaseDataSource = FirebaseUserDataSource(
        firebaseAuth = firebaseAuth,
        firestore = firestore,
        firebaseUserToUserMapper = firebaseUserToUserMapper,
        ioDispatcher = coroutineRule.dispatcher
    )

    @Test
    fun `GIVEN there is a FirebaseUser WHEN getUser RETURN mapped User`() = runTest {
        //GIVEN
        val user = User(
            id = "123456",
            name = "User Fake",
            email = "userfake@domain.com",
            photoUrl = "www.domain.com/photos/userFake.jpg"
        )
        every { firebaseAuth.currentUser } returns mockk()
        every { firebaseUserToUserMapper.mapFrom(any()) } returns user
        //WHEN
        val result = firebaseDataSource.getCurrentUser()
        //THEN
        assertEquals(user.name, result.name)
        assertEquals(user.id, result.id)
        assertEquals(user.email, result.email)
        assertEquals(user.photoUrl, result.photoUrl)
    }

    @Test
    fun `GIVEN there is not a FirebaseUser WHEN getUser THROWN UserNotFoundException`() = runTest {
        //GIVEN
        every { firebaseAuth.currentUser } returns null
        //WHEN
        val exception = assertFails { firebaseDataSource.getCurrentUser() }
        //THEN
        assertTrue(exception is UserException.UserNotFoundException)
    }
}