package br.com.jwar.sharedbill.data.datasources

import br.com.jwar.sharedbill.core.ZERO

import br.com.jwar.sharedbill.core.orZero
import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.exceptions.GroupException.GroupNotFoundException
import br.com.jwar.sharedbill.domain.exceptions.GroupException.RemoveMemberWithNonZeroBalanceException
import br.com.jwar.sharedbill.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.snapshots
import java.math.RoundingMode
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseGroupDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseUserToUserMapper: FirebaseUserToUserMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupsDataSource {

    companion object {
        const val GROUPS_REF = "groups"
        const val GROUP_ID_FIELD = "id"
        const val GROUP_TITLE_FIELD = "title"
        const val GROUP_MEMBERS_FIELD = "members"
        const val GROUP_FIREBASE_MEMBERS_IDS_FIELD = "firebaseMembersIds"
        const val GROUP_INVITES_FIELD = "invites"
        const val GROUP_PAYMENTS_FIELD = "payments"
        const val UNPROCESSED_PAYMENTS_REF = "unprocessedPayments"
        const val PAYMENTS_IDS_FIELD = "paymentsIds"
        const val GROUP_BALANCE_FIELD = "balance"
    }

    override suspend fun getGroupById(groupId: String): Group {
        return withContext(ioDispatcher) {
            return@withContext getUserGroupsQuery()
                .whereEqualTo(GROUP_ID_FIELD, groupId)
                .get()
                .await()
                .documents
                .map { getGroupFromSnapshot(it) }
                .first()
        }
    }

    override fun getGroupByIdFlow(groupId: String): Flow<Resource<Group>> {
        return getUserGroupsQuery()
            .whereEqualTo(GROUP_ID_FIELD, groupId)
            .snapshots()
            .map { snapshot ->
                Resource.Success(getGroupFromSnapshot(snapshot.documents.firstOrNull()))
            }
    }

    override suspend fun getAllGroups(): List<Group> {
        return withContext(ioDispatcher) {
            return@withContext getUserGroupsQuery()
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Group::class.java) }
        }
    }

    override suspend fun createGroup(group: Group): Group {
        val firebaseUser = getCurrentUser()
        return withContext(ioDispatcher) {
            val groupDoc = firestore.collection(GROUPS_REF).document()
            val user = firebaseUserToUserMapper.mapFrom(firebaseUser)
                .copy(uid = UUID.randomUUID().toString())
            val newGroup = group.copy(
                id = groupDoc.id,
                owner = user,
                members = listOf(user),
                firebaseMembersIds = listOf(user.firebaseUserId)
            )
            groupDoc.set(newGroup); newGroup
        }
    }

    override suspend fun saveGroup(groupId: String, title: String): Group {
        return withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/${groupId}")
            groupDoc.update(mapOf(GROUP_TITLE_FIELD to title,))
            return@withContext getGroupById(groupId)
        }
    }

    override suspend fun addMember(user: User, groupId: String): Group {
        return withContext(ioDispatcher) {
            val groupDoc = firestore.document("$GROUPS_REF/$groupId")
            groupDoc.update(mapOf(
                GROUP_MEMBERS_FIELD to FieldValue.arrayUnion(user),
                GROUP_INVITES_FIELD to FieldValue.arrayUnion(user.inviteCode)
            ))
            return@withContext getGroupById(groupId)
        }
    }

    override suspend fun removeMember(userId: String, groupId: String): Group {
        return withContext(ioDispatcher) {
            val group = firestore.collection(GROUPS_REF)
                .whereEqualTo(GROUP_ID_FIELD, groupId)
                .get()
                .await()
                .documents
                .firstOrNull()?.toObject(Group::class.java) ?: throw GroupNotFoundException
            val user = group.members.firstOrNull { it.uid == userId } ?: throw UserNotFoundException

            if (group.balance[userId].orZero() != ZERO)
                throw RemoveMemberWithNonZeroBalanceException

            val groupDoc = firestore.document("$GROUPS_REF/${group.id}")
            groupDoc.update(mapOf(
                GROUP_MEMBERS_FIELD to FieldValue.arrayRemove(user),
                GROUP_FIREBASE_MEMBERS_IDS_FIELD to FieldValue.arrayRemove(user.firebaseUserId),
            ))
            return@withContext getGroupById(group.id)
        }
    }

    override suspend fun joinGroup(code: String) : Group {
        return withContext(ioDispatcher) {
            val group = firestore.collection(GROUPS_REF)
                .whereArrayContains(GROUP_INVITES_FIELD, code)
                .get()
                .await()
                .documents
                .firstOrNull()?.toObject(Group::class.java) ?: throw GroupNotFoundException

            val invitedUser = group.members.firstOrNull { it.inviteCode == code } ?: throw UserNotFoundException
            val firebaseUser = getCurrentUser()
            val joinedUser = invitedUser.copy(
                firebaseUserId = firebaseUser.uid,
                inviteCode = "",
                photoUrl = firebaseUser.photoUrl.toString(),
                email = firebaseUser.email.orEmpty()
            )
            val groupDoc = firestore.document("$GROUPS_REF/${group.id}")
            groupDoc.update(mapOf(
                GROUP_MEMBERS_FIELD to FieldValue.arrayRemove(invitedUser),
                GROUP_INVITES_FIELD to FieldValue.arrayRemove(invitedUser.inviteCode),
            ))
            groupDoc.update(mapOf(
                GROUP_MEMBERS_FIELD to FieldValue.arrayUnion(joinedUser),
                GROUP_FIREBASE_MEMBERS_IDS_FIELD to FieldValue.arrayUnion(firebaseUser.uid)
            ))
            return@withContext getGroupById(group.id)
        }
    }

    override suspend fun sendPayment(payment: Payment, groupId: String): Group {
        return withContext(ioDispatcher) {
            if (payment.description.isEmpty()) throw PaymentException.EmptyDescriptionException
            if (payment.value.isEmpty()) throw PaymentException.EmptyValueException
            if (payment.paidTo.isEmpty()) throw PaymentException.EmptyRelatedMembersException

            val groupDoc = firestore.document("$GROUPS_REF/${groupId}")
            val groupSnapshot = groupDoc.get().await()
            val group = groupSnapshot.toObject(Group::class.java) ?: throw GroupNotFoundException
            val unprocessedPaymentDoc = firestore.document("$UNPROCESSED_PAYMENTS_REF/${groupId}")

            val unprocessedPaymentData = mapOf(PAYMENTS_IDS_FIELD to FieldValue.arrayUnion(payment.id))
            unprocessedPaymentDoc.set(unprocessedPaymentData, SetOptions.merge())

            val groupData = mapOf(GROUP_PAYMENTS_FIELD to FieldValue.arrayUnion(payment))
            groupDoc.update(groupData)

            return@withContext group
        }
    }

    private fun getGroupFromSnapshot(snapshot: DocumentSnapshot?): Group {
        val group = snapshot?.toObject(Group::class.java) ?: throw GroupNotFoundException
        return group.withUpdatedBalanceOffline()
    }

    private fun Group.withUpdatedBalanceOffline(): Group {
        val balance = this.balance.toMutableMap()
        this.payments.filter { it.processed.not() }.forEach { payment ->
            val total = payment.value.toBigDecimal().orZero().setScale(2, RoundingMode.CEILING)
            val shared = total.div(payment.paidTo.size.toBigDecimal()).setScale(2, RoundingMode.CEILING)

            payment.paidTo.forEach { member ->
                balance[member.uid] =
                    balance[member.uid]?.toBigDecimal().orZero().plus(shared).toString()
            }
            balance[payment.paidBy.uid] = balance[payment.paidBy.uid]?.toBigDecimal()?.minus(total).toString()
        }
        return this.copy(balance = balance)
    }

    private fun getCurrentUser() = firebaseAuth.currentUser ?: throw UserNotFoundException

    private fun getUserGroupsQuery(): Query {
        val firebaseUser = getCurrentUser()
        return firestore.collection(GROUPS_REF)
            .whereArrayContains(GROUP_FIREBASE_MEMBERS_IDS_FIELD, firebaseUser.uid)
    }
}