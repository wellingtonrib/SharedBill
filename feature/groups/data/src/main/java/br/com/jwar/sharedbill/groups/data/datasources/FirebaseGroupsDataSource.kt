package br.com.jwar.sharedbill.groups.data.datasources

import br.com.jwar.sharedbill.account.domain.exceptions.UserException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.NetworkManager
import br.com.jwar.sharedbill.core.utility.extensions.orZero
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException.GroupNotFoundException
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.util.UUID
import javax.inject.Inject

class FirebaseGroupsDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val networkManager: NetworkManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val exceptionHandler: ExceptionHandler,
) : GroupsDataSource {

    companion object {
        const val GROUPS_REF = "groups"
        const val GROUP_ID_FIELD = "id"
        const val GROUP_TITLE_FIELD = "title"
        const val GROUP_MEMBERS_FIELD = "members"
        const val GROUP_FIREBASE_MEMBERS_IDS_FIELD = "firebaseMembersIds"
        const val GROUP_INVITES_FIELD = "invites"
        const val GROUP_PAYMENTS_FIELD = "payments"
        const val UNPROCESSED_PAYMENTS_REF = "unprocessedPayments"
        const val PAYMENT_GROUP_ID_FIELD = "groupId"
    }

    override suspend fun getGroupsStream(): Flow<List<Group>> =
        getUserGroupsQuery()
            .asFlow()
            .map { docs -> docs.mapNotNull { tryMapGroupFromSnapshot(it) } }
            .flowOn(ioDispatcher)

    override suspend fun getGroupByIdStream(groupId: String) =
        getUserGroupsQuery()
            .whereEqualTo(GROUP_ID_FIELD, groupId)
            .asFlow()
            .map { mapGroupFromSnapshot(it.firstOrNull()) }
            .flowOn(ioDispatcher)

    override suspend fun getGroupById(groupId: String, refresh: Boolean) =
        withContext(ioDispatcher) {
            getUserGroupsQuery()
                .whereEqualTo(GROUP_ID_FIELD, groupId)
                .get(if (refresh && networkManager.isConnected()) Source.SERVER else Source.CACHE)
                .await()
                .documents
                .firstOrNull()
                .let { mapGroupFromSnapshot(it) }
        }

    override suspend fun getGroupByInviteCode(inviteCode: String) =
        withContext(ioDispatcher) {
            firestore.collection(GROUPS_REF)
                .whereArrayContains(GROUP_INVITES_FIELD, inviteCode)
                .get()
                .await()
                .documents
                .firstOrNull()
                .let { mapGroupFromSnapshot(it) }
        }

    override suspend fun createGroup(group: Group): String =
        withContext(ioDispatcher) {
            val groupDoc = firestore.collection(GROUPS_REF).document()
            val newGroup = group.copy(id = groupDoc.id)
            groupDoc.set(newGroup)
            newGroup.id
        }

    override suspend fun updateGroup(groupId: String, title: String): Unit =
        withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/$groupId")
            groupDoc.update(mapOf(GROUP_TITLE_FIELD to title,))
        }

    override suspend fun addMember(user: User, groupId: String): Unit =
        withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/$groupId")
            groupDoc.update(
                mapOf(
                    GROUP_MEMBERS_FIELD to FieldValue.arrayUnion(user),
                    GROUP_INVITES_FIELD to FieldValue.arrayUnion(user.inviteCode)
                )
            )
        }

    override suspend fun removeMember(user: User, groupId: String): Unit =
        withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/$groupId")
            groupDoc.update(
                mapOf(
                    GROUP_MEMBERS_FIELD to FieldValue.arrayRemove(user),
                    GROUP_FIREBASE_MEMBERS_IDS_FIELD to FieldValue.arrayRemove(user.firebaseUserId),
                )
            )
        }

    override suspend fun joinGroup(groupId: String, inviteCode: String, joinedUser: User): Unit =
        withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/$groupId")
            val groupSnapshot = groupDoc.get().await()
            val membersList = groupSnapshot?.toObject(Group::class.java)?.members?.toMutableList()
            val memberIndex = membersList?.indexOfFirst { it.inviteCode == inviteCode }
            if (memberIndex == null || memberIndex == -1) throw GroupException.InvalidInviteCodeException

            membersList[memberIndex] = joinedUser

            groupDoc.update(
                mapOf(
                    GROUP_MEMBERS_FIELD to membersList,
                    GROUP_FIREBASE_MEMBERS_IDS_FIELD to FieldValue.arrayUnion(joinedUser.firebaseUserId),
                    GROUP_INVITES_FIELD to FieldValue.arrayRemove(inviteCode)
                )
            )
        }

    override suspend fun sendPayment(payment: Payment): Unit =
        withContext(ioDispatcher) {
            firestore.document("$GROUPS_REF/${payment.groupId}")
                .update(mapOf(GROUP_PAYMENTS_FIELD to FieldValue.arrayUnion(payment)))
            firestore.collection(UNPROCESSED_PAYMENTS_REF).document(payment.id).set(payment)
        }

    override suspend fun deletePayment(payment: Payment, refundSuffix: String): Unit =
        withContext(ioDispatcher) {
            try {
                firestore.runTransaction { transaction ->
                    val groupDoc = firestore.document("$GROUPS_REF/${payment.groupId}")
                    val groupSnapshot = transaction.get(groupDoc)
                    val currentPayments = groupSnapshot.toObject(Group::class.java)?.payments.orEmpty()
                    val updatedPayment = currentPayments.map {
                        if (it.id == payment.id) it.copy(reversed = true) else it
                    }
                    val reversePayment = payment.paidTo.entries.map { (memberId, weight) ->
                        val refundValue = payment.value.toBigDecimal()
                            .multiply(weight.toBigDecimal())
                            .div(payment.paidTo.values.sum().toBigDecimal())
                            .setScale(2, RoundingMode.HALF_EVEN)
                        val refund = Payment(
                            groupId = payment.groupId,
                            id = UUID.randomUUID().toString(),
                            description = payment.description.plus(" ($refundSuffix)"),
                            value = refundValue.toString(),
                            paidBy = memberId,
                            paidTo = mapOf(payment.paidBy to 1),
                            paymentType = PaymentType.REVERSE
                        )
                        val unprocessedRefundDoc = firestore
                            .collection(UNPROCESSED_PAYMENTS_REF)
                            .document(refund.id)
                        transaction.set(unprocessedRefundDoc, refund)
                        refund
                    }
                    transaction.update(groupDoc, GROUP_PAYMENTS_FIELD, updatedPayment + reversePayment)
                    null
                }
            } catch (e: FirebaseFirestoreException) {
                e.printStackTrace()
            }
        }

    override suspend fun deleteGroup(groupId: String) {
        val groupDoc = firestore.document("$GROUPS_REF/$groupId")
        groupDoc.delete()
    }

    private suspend fun mapGroupFromSnapshot(snapshot: DocumentSnapshot?): Group {
        val group = snapshot?.toObject(Group::class.java) ?: throw GroupNotFoundException
        val groupUnprocessedPayments = getUnprocessedPayments(group.id)
        return group.byProcessingPayments(groupUnprocessedPayments).byHandlingCurrentUser()
    }

    @Suppress("TooGenericExceptionCaught")
    private suspend fun tryMapGroupFromSnapshot(snapshot: DocumentSnapshot?): Group? =
        try {
            mapGroupFromSnapshot(snapshot)
        } catch (e: Exception) {
            exceptionHandler.recordException(e)
            null
        }

    private suspend fun getUnprocessedPayments(groupId: String) =
        withContext(ioDispatcher) {
            return@withContext firestore.collection(UNPROCESSED_PAYMENTS_REF)
                .whereEqualTo(PAYMENT_GROUP_ID_FIELD, groupId)
                .get()
                .await()
                .toObjects(Payment::class.java)
        }

    private fun Group.byHandlingCurrentUser(): Group {
        val updatedGroup = this.copy(
            members = this.members.map {
                it.copy(isCurrentUser = firebaseAuth.currentUser?.uid == it.firebaseUserId)
            }
        )
        return updatedGroup
    }

    private fun Group.byProcessingPayments(groupUnprocessedPayments: List<Payment>): Group {
        val balance = this.balance.toMutableMap()

        groupUnprocessedPayments.forEach { payment ->
            val valueToShare = payment.value.toBigDecimal().orZero()
            val paidToWeights = payment.paidTo.values.sum()

            payment.paidTo.forEach { (memberId, weight) ->
                val sharedValue = valueToShare
                    .multiply(weight.toBigDecimal())
                    .divide(paidToWeights.toBigDecimal(), 2, RoundingMode.HALF_UP)
                val adjustment = if (memberId == payment.paidBy) {
                    -(valueToShare - sharedValue)
                } else {
                    sharedValue
                }
                val currentBalance = balance[memberId]?.toBigDecimal().orZero()
                balance[memberId] = currentBalance.plus(adjustment)
                    .setScale(2, RoundingMode.HALF_UP).toString()
            }

            if (payment.paidTo.containsKey(payment.paidBy).not()) {
                val currentBalance = balance[payment.paidBy]?.toBigDecimal().orZero()
                balance[payment.paidBy] = currentBalance.minus(valueToShare)
                    .setScale(2, RoundingMode.HALF_UP).toString()
            }
        }
        return this.copy(balance = balance)
    }

    private fun getUserGroupsQuery(): Query {
        val firebaseUser = firebaseAuth.currentUser ?: throw UserException.UserNotFoundException
        return firestore.collection(GROUPS_REF)
            .whereArrayContains(GROUP_FIREBASE_MEMBERS_IDS_FIELD, firebaseUser.uid)
    }

    @ExperimentalCoroutinesApi
    fun Query.asFlow(): Flow<List<DocumentSnapshot>> = callbackFlow {
        val listenerRegistration = this@asFlow
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                trySend(value?.documents.orEmpty())
            }
        awaitClose { listenerRegistration.remove() }
    }
}
