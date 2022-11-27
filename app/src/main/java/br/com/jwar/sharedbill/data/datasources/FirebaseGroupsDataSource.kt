package br.com.jwar.sharedbill.data.datasources

import br.com.jwar.sharedbill.core.orZero
import br.com.jwar.sharedbill.domain.datasources.GroupsRemoteDataSource
import br.com.jwar.sharedbill.domain.exceptions.GroupException.GroupNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import javax.inject.Inject

class FirebaseGroupsDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupsRemoteDataSource {

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

    override suspend fun getGroupsStream() =
        getUserGroupsQuery()
            .snapshots()
            .map { mapResultFromSnapshots(it.documents) }
            .flowOn(ioDispatcher)

    override suspend fun getGroupByIdStream(groupId: String) =
        getUserGroupsQuery()
            .whereEqualTo(GROUP_ID_FIELD, groupId)
            .snapshots()
            .map { mapResultFromSnapshot(it.documents.first()) }
            .flowOn(ioDispatcher)

    override suspend fun getGroupById(groupId: String) =
        withContext(ioDispatcher) {
            getUserGroupsQuery()
                .whereEqualTo(GROUP_ID_FIELD, groupId)
                .get()
                .await()
                .documents
                .firstOrNull()
                .let { mapResultFromSnapshot(it) }
        }

    override suspend fun getGroupByInviteCode(inviteCode: String) =
        withContext(ioDispatcher) {
            firestore.collection(GROUPS_REF)
                .whereArrayContains(GROUP_INVITES_FIELD, inviteCode)
                .get()
                .await()
                .documents
                .firstOrNull()
                .let { mapResultFromSnapshot(it) }
        }

    override suspend fun saveGroup(group: Group) =
        withContext(ioDispatcher) {
            val groupDoc = firestore.collection(GROUPS_REF).document()
            val newGroup = group.copy(id = groupDoc.id)
            groupDoc.set(newGroup)
            return@withContext getGroupById(groupDoc.id)
        }

    override suspend fun updateGroup(groupId: String, title: String) =
        withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/${groupId}")
            groupDoc.update(mapOf(GROUP_TITLE_FIELD to title,))
            return@withContext getGroupById(groupId)
        }

    override suspend fun addMember(user: User, groupId: String) =
        withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/$groupId")
            groupDoc.update(mapOf(
                GROUP_MEMBERS_FIELD to FieldValue.arrayUnion(user),
                GROUP_INVITES_FIELD to FieldValue.arrayUnion(user.inviteCode)
            ))
            return@withContext getGroupById(groupId)
        }

    override suspend fun removeMember(user: User, groupId: String) =
        withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/${groupId}")
            groupDoc.update(mapOf(
                GROUP_MEMBERS_FIELD to FieldValue.arrayRemove(user),
                GROUP_FIREBASE_MEMBERS_IDS_FIELD to FieldValue.arrayRemove(user.firebaseUserId),
            ))
            return@withContext getGroupById(groupId)
        }

    override suspend fun joinGroup(groupId: String, invitedUser: User, joinedUser: User) =
        withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/${groupId}")
            groupDoc.update(mapOf(
                GROUP_MEMBERS_FIELD to FieldValue.arrayRemove(invitedUser),
                GROUP_INVITES_FIELD to FieldValue.arrayRemove(invitedUser.inviteCode),
            ))
            groupDoc.update(mapOf(
                GROUP_MEMBERS_FIELD to FieldValue.arrayUnion(joinedUser),
                GROUP_FIREBASE_MEMBERS_IDS_FIELD to FieldValue.arrayUnion(joinedUser.firebaseUserId)
            ))
            return@withContext getGroupById(groupId)
        }

    override suspend fun sendPayment(payment: Payment) =
        withContext(ioDispatcher) {
            firestore.document("$GROUPS_REF/${payment.groupId}")
                .update(mapOf(GROUP_PAYMENTS_FIELD to FieldValue.arrayUnion(payment)))
            firestore.collection(UNPROCESSED_PAYMENTS_REF).document(payment.id).set(payment)
            return@withContext getGroupById(payment.groupId)
        }

    private suspend fun mapResultFromSnapshots(snapshots: MutableList<DocumentSnapshot>) =
        snapshots.mapNotNull { mapGroupFromSnapshot(it) }.let { Result.success(it) }

    private suspend fun mapResultFromSnapshot(snapshot: DocumentSnapshot?) =
        mapGroupFromSnapshot(snapshot)?.let { Result.success(it) }
            ?: kotlin.run { Result.failure(GroupNotFoundException) }

    private suspend fun mapGroupFromSnapshot(snapshot: DocumentSnapshot?): Group? {
        val group = snapshot?.toObject(Group::class.java)
        val groupUnprocessedPayments = getUnprocessedPayments(group?.id.orEmpty())
        return group?.byProcessingPayments(groupUnprocessedPayments)?.byHandlingCurrentUser()
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
        val updatedGroup = this.copy(members = this.members.map {
            it.copy(isCurrentUser = firebaseAuth.currentUser?.uid == it.firebaseUserId)
        })
        return updatedGroup
    }

    private fun Group.byProcessingPayments(groupUnprocessedPayments: List<Payment>): Group {
        val balance = this.balance.toMutableMap()
        groupUnprocessedPayments.forEach { payment ->
            val total = payment.value.toBigDecimal().orZero().setScale(2, RoundingMode.CEILING)
            val shared = total.div(payment.paidTo.size.toBigDecimal()).setScale(2, RoundingMode.CEILING)

            payment.paidTo.forEach { member ->
                balance[member.id] =
                    balance[member.id]?.toBigDecimal().orZero().plus(shared).toString()
            }
            balance[payment.paidBy.id] = balance[payment.paidBy.id]?.toBigDecimal()?.minus(total).toString()
        }
        return this.copy(balance = balance)
    }

    private fun getUserGroupsQuery(): Query {
        return firestore.collection(GROUPS_REF)
            .whereArrayContains(GROUP_FIREBASE_MEMBERS_IDS_FIELD, firebaseAuth.currentUser?.uid.orEmpty())
    }
}