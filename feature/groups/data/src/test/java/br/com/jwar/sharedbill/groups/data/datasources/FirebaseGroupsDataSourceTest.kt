package br.com.jwar.sharedbill.groups.data.datasources

import br.com.jwar.sharedbill.core.utility.NetworkManager
import br.com.jwar.sharedbill.groups.data.datasources.FirebaseGroupsDataSource.Companion.GROUPS_REF
import br.com.jwar.sharedbill.groups.data.datasources.FirebaseGroupsDataSource.Companion.GROUP_FIREBASE_MEMBERS_IDS_FIELD
import br.com.jwar.sharedbill.groups.data.datasources.FirebaseGroupsDataSource.Companion.GROUP_ID_FIELD
import br.com.jwar.sharedbill.groups.data.datasources.FirebaseGroupsDataSource.Companion.PAYMENT_GROUP_ID_FIELD
import br.com.jwar.sharedbill.groups.data.datasources.FirebaseGroupsDataSource.Companion.UNPROCESSED_PAYMENTS_REF
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs

internal class FirebaseGroupsDataSourceTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val firebaseAuth: FirebaseAuth = mockk()
    private val firestore: FirebaseFirestore = mockk()
    private val networkManager: NetworkManager = mockk()

    private val firebaseGroupsDataSource = FirebaseGroupsDataSource(
        firebaseAuth = firebaseAuth,
        firestore = firestore,
        networkManager = networkManager,
        ioDispatcher = coroutineRule.dispatcher
    )

    @Test
    fun `getGroupById returns expected group`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val expected = Group(id = groupId, title = "Test Group")
        prepareScenario(groupId = groupId, group = expected)

        val result = firebaseGroupsDataSource.getGroupById(groupId, refresh = false)

        assertEquals(expected, result)
    }

    @Test
    fun `getGroupById throws GroupNotFoundException`() = runTest {
        val groupId = UUID.randomUUID().toString()
        prepareScenario(groupId = groupId, groupsSnapshots = emptyList())

        val exception = assertFails { firebaseGroupsDataSource.getGroupById(groupId, refresh = false) }

        assertIs<GroupException.GroupNotFoundException>(exception)
    }

    @Test
    fun `getGroupById with refresh false get from CACHE`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val groupsQuery: Query = mockk()
        prepareScenario(groupId = groupId, groupsQuery = groupsQuery)

        firebaseGroupsDataSource.getGroupById(groupId, refresh = false)

        verify { groupsQuery.get(Source.CACHE) }
    }

    @Test
    fun `getGroupById with refresh true get from SERVER`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val groupsQuery: Query = mockk()
        prepareScenario(groupId = groupId, groupsQuery = groupsQuery)

        firebaseGroupsDataSource.getGroupById(groupId, refresh = true)

        verify { groupsQuery.get(Source.SERVER) }
    }

    @Test
    fun `getGroupById with unprocessed payments should process payments`() = runTest {

    }

    private fun prepareScenario(
        firebaseUser: FirebaseUser = mockk(),
        firebaseUserUid: String = UUID.randomUUID().toString(),
        groupId: String = UUID.randomUUID().toString(),
        group: Group = Group(id = groupId, title = "Test Group"),
        groupsCollectionReference: CollectionReference = mockk(),
        groupsQuery: Query = mockk(),
        groupsQueryTask: Task<QuerySnapshot> = mockk(),
        groupsQuerySnapshot: QuerySnapshot = mockk(),
        groupsSnapshot: DocumentSnapshot = mockk(),
        groupsSnapshots: List<DocumentSnapshot> = listOf(groupsSnapshot),
        unprocessedPaymentsCollection: CollectionReference = mockk(),
        unprocessedPaymentsQuery: Query = mockk(),
        unprocessedPaymentsQueryTask: Task<QuerySnapshot> = mockk(),
        unprocessedPaymentsSnapshot: QuerySnapshot = mockk(),
        unprocessedPayments: List<Payment> = emptyList(),
    ) {
        every { firebaseAuth.currentUser } returns firebaseUser
        every { firebaseUser.uid } returns firebaseUserUid
        every { firestore.collection(GROUPS_REF) } returns groupsCollectionReference
        every { groupsCollectionReference.whereArrayContains(GROUP_FIREBASE_MEMBERS_IDS_FIELD, firebaseUserUid) } returns groupsQuery
        every { groupsQuery.whereEqualTo(GROUP_ID_FIELD, groupId) } returns groupsQuery
        coEvery { groupsQuery.get(any()) } returns groupsQueryTask
        coEvery { groupsQueryTask.isSuccessful } returns true
        coEvery { groupsQueryTask.isComplete } returns true
        coEvery { groupsQueryTask.isCanceled } returns false
        coEvery { groupsQueryTask.result } returns groupsQuerySnapshot
        coEvery { groupsQueryTask.exception } returns null
        coEvery { groupsQuerySnapshot.documents } returns groupsSnapshots
        coEvery { groupsSnapshot.toObject(Group::class.java) } returns group
        every { firestore.collection(UNPROCESSED_PAYMENTS_REF) } returns unprocessedPaymentsCollection
        every { unprocessedPaymentsCollection.whereEqualTo(PAYMENT_GROUP_ID_FIELD, groupId) } returns unprocessedPaymentsQuery
        coEvery { unprocessedPaymentsQuery.get() } returns unprocessedPaymentsQueryTask
        coEvery { unprocessedPaymentsQueryTask.isSuccessful } returns true
        coEvery { unprocessedPaymentsQueryTask.isComplete } returns true
        coEvery { unprocessedPaymentsQueryTask.isCanceled } returns false
        coEvery { unprocessedPaymentsQueryTask.result } returns unprocessedPaymentsSnapshot
        coEvery { unprocessedPaymentsQueryTask.exception } returns null
        coEvery { unprocessedPaymentsSnapshot.toObjects(Payment::class.java) } returns unprocessedPayments
    }
}