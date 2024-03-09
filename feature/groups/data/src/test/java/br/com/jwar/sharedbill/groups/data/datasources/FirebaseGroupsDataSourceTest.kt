package br.com.jwar.sharedbill.groups.data.datasources

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
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
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
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
    private val exceptionHandler: ExceptionHandler = mockk()

    private val firebaseGroupsDataSource = FirebaseGroupsDataSource(
        firebaseAuth = firebaseAuth,
        firestore = firestore,
        networkManager = networkManager,
        ioDispatcher = coroutineRule.dispatcher,
        exceptionHandler = exceptionHandler
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
    fun `getGroupById with refresh true but not connected get from CACHE`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val groupsQuery: Query = mockk()
        prepareScenario(isConnected = false, groupId = groupId, groupsQuery = groupsQuery)

        firebaseGroupsDataSource.getGroupById(groupId, refresh = false)

        verify { groupsQuery.get(Source.CACHE) }
    }

    @Test
    fun `getGroupById with refresh true get from SERVER`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val groupsQuery: Query = mockk()
        prepareScenario(isConnected = true, groupId = groupId, groupsQuery = groupsQuery)

        firebaseGroupsDataSource.getGroupById(groupId, refresh = true)

        verify { groupsQuery.get(Source.SERVER) }
    }

    @Test
    fun `getGroupById with unprocessed payments should update balance equality`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val unprocessedPayments = listOf(
            Payment(
                groupId = groupId,
                value = "3000",
                paidBy = "payer",
                paidTo = mapOf(
                    "payer" to 1,
                    "other" to 1,
                    "another" to 1
                )
            )
        )
        prepareScenario(groupId = groupId, unprocessedPayments = unprocessedPayments)

        val result = firebaseGroupsDataSource.getGroupById(groupId, refresh = false)

        assertEquals(
            mapOf(
                "payer" to "-2000.00",
                "other" to "1000.00",
                "another" to "1000.00",
            ),
            result.balance
        )
    }

    @Test
    fun `getGroupById with unprocessed payments should update balance based on weight`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val unprocessedPayments = listOf(
            Payment(
                groupId = groupId,
                value = "4000",
                paidBy = "payer",
                paidTo = mapOf(
                    "payer" to 2,
                    "other" to 1,
                    "another" to 1
                )
            )
        )
        prepareScenario(groupId = groupId, unprocessedPayments = unprocessedPayments)

        val result = firebaseGroupsDataSource.getGroupById(groupId, refresh = false)

        assertEquals(
            mapOf(
                "payer" to "-2000.00",
                "other" to "1000.00",
                "another" to "1000.00",
            ),
            result.balance
        )
    }

    @Test
    fun `getGroupById with unprocessed payments should update balance of payer`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val unprocessedPayments = listOf(
            Payment(
                groupId = groupId,
                value = "1000",
                paidBy = "payer",
                paidTo = mapOf(
                    "other" to 1,
                    "another" to 1
                )
            )
        )
        prepareScenario(groupId = groupId, unprocessedPayments = unprocessedPayments)

        val result = firebaseGroupsDataSource.getGroupById(groupId, refresh = false)

        assertEquals(
            mapOf(
                "payer" to "-1000.00",
                "other" to "500.00",
                "another" to "500.00",
            ),
            result.balance
        )
    }

    @Test
    fun `getGroupById with unprocessed payments should update balance correctly`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val group = Group(
            id = groupId,
            title = "Test Group",
            balance = mapOf(
                "m1" to "0.00",
                "m2" to "0.00",
                "m3" to "0.00",
            )
        )
        val unprocessedPayments = listOf(
            Payment(
                groupId = group.id,
                value = "100",
                paidBy = "m1",
                paidTo = mapOf(
                    "m1" to 1,
                    "m2" to 1,
                    "m3" to 1
                )
            )
        )
        prepareScenario(groupId = groupId, group = group, unprocessedPayments = unprocessedPayments)

        val result = firebaseGroupsDataSource.getGroupById(group.id, refresh = false)

        assertEquals(
            mapOf(
                "m1" to "-66.67",
                "m2" to "33.33",
                "m3" to "33.33",
            ),
            result.balance
        )
    }

    @Test
    fun `getGroupById with unprocessed settlement payments should update balance correctly`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val group = Group(
            id = groupId,
            title = "Test Group",
            balance = mapOf(
                "owes" to "33.33",
                "owned" to "-33.34",
            )
        )
        val unprocessedPayments = listOf(
            Payment(
                groupId = group.id,
                value = "33.33",
                paidBy = "owes",
                paidTo = mapOf(
                    "owned" to 1
                )
            )
        )
        prepareScenario(groupId = groupId, group = group, unprocessedPayments = unprocessedPayments)

        val result = firebaseGroupsDataSource.getGroupById(group.id, refresh = false)

        assertEquals(
            mapOf(
                "owes" to "0.00",
                "owned" to "-0.01",
            ),
            result.balance
        )
    }

    @Suppress("LongParameterList")
    private fun prepareScenario(
        isConnected: Boolean = true,
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
        every { networkManager.isConnected() } returns isConnected
        every { firebaseAuth.currentUser } returns firebaseUser
        every { firebaseUser.uid } returns firebaseUserUid
        every { firestore.collection(GROUPS_REF) } returns groupsCollectionReference
        every {
            groupsCollectionReference
                .whereArrayContains(GROUP_FIREBASE_MEMBERS_IDS_FIELD, firebaseUserUid)
        } returns groupsQuery
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
        every {
            unprocessedPaymentsCollection.whereEqualTo(PAYMENT_GROUP_ID_FIELD, groupId)
        } returns unprocessedPaymentsQuery
        coEvery { unprocessedPaymentsQuery.get() } returns unprocessedPaymentsQueryTask
        coEvery { unprocessedPaymentsQueryTask.isSuccessful } returns true
        coEvery { unprocessedPaymentsQueryTask.isComplete } returns true
        coEvery { unprocessedPaymentsQueryTask.isCanceled } returns false
        coEvery { unprocessedPaymentsQueryTask.result } returns unprocessedPaymentsSnapshot
        coEvery { unprocessedPaymentsQueryTask.exception } returns null
        coEvery { unprocessedPaymentsSnapshot.toObjects(Payment::class.java) } returns unprocessedPayments
        every { exceptionHandler.recordException(any()) } just runs
    }
}
