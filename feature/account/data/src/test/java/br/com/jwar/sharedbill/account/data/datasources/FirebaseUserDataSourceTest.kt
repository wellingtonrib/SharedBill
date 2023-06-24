package br.com.jwar.sharedbill.account.data.datasources

import br.com.jwar.sharedbill.account.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.account.domain.exceptions.UserException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import br.com.jwar.sharedbill.testing.Fakes
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs


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
    fun `getCurrentUser should call firebase user and return a domain user`() = runTest {
        val firebaseUser = mockk<FirebaseUser>()
        val expectedUser = mockk<User>()
        prepareScenario(firebaseUser = firebaseUser, user = expectedUser)

        val currentUser = firebaseDataSource.getCurrentUser()

        verify { firebaseUserToUserMapper.mapFrom(firebaseUser) }
        assertEquals(expectedUser, currentUser)
    }

    @Test
    fun `getCurrentUserException without a firebase user should throw UserNotFoundException`() = runTest {
        prepareScenario(firebaseUser = null)

        val exception = assertFails { firebaseDataSource.getCurrentUser() }

        assertIs<UserException.UserNotFoundException>(exception)
    }

    @Test
    fun `createUser should create a firestore document and set user`() = runTest {
        val userName = "User Name"
        val documentId = "123"
        val documentReference: DocumentReference = mockk {
            every { id } returns documentId
        }
        prepareScenario(documentReference = documentReference)

        firebaseDataSource.createUser(userName)

        verify { documentReference.set(User(id = documentId, name = userName)) }
    }

    @Test
    fun `saveUser should get firestore document from firebaseUserId and set user`() = runTest  {
        val user = Fakes.makeUser()
        val documentReference = mockk<DocumentReference>()
        prepareScenario(documentReference = documentReference)

        firebaseDataSource.saveUser(user)

        coVerify { documentReference.set(user) }
    }

    private fun prepareScenario(
        firebaseUser: FirebaseUser? = mockk(),
        user: User = mockk(),
        collectionReference: CollectionReference = mockk(),
        documentReference: DocumentReference = mockk(),
        task: Task<Void> = mockk()
    ) {
        every { firebaseAuth.currentUser } returns firebaseUser
        every { firebaseUserToUserMapper.mapFrom(any()) } returns user
        coEvery { firestore.collection(any()) } returns collectionReference
        every { collectionReference.document() } returns documentReference
        every { collectionReference.document(any()) } returns documentReference
        coEvery { documentReference.set(any()) } returns task
        every { task.isComplete } returns true
        every { task.exception } returns null
        every { task.isCanceled } returns false
        every { task.result } returns mockk()
    }
}