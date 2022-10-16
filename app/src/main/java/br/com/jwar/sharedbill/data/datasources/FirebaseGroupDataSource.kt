package br.com.jwar.sharedbill.data.datasources

import br.com.jwar.sharedbill.core.ZERO
import br.com.jwar.sharedbill.core.orZero
import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.exceptions.GroupBalanceException
import br.com.jwar.sharedbill.domain.exceptions.GroupNotFoundException
import br.com.jwar.sharedbill.domain.exceptions.PaymentInvalidException
import br.com.jwar.sharedbill.domain.exceptions.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.math.RoundingMode
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

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
        const val GROUP_BALANCE_FIELD = "balance"
    }

    override suspend fun getGroupById(groupId: String): Group {
        return withContext(ioDispatcher) {
            return@withContext getUserGroupsQuery()
                .whereEqualTo(GROUP_ID_FIELD, groupId)
                .get()
                .await()
                .documents
                .firstOrNull()?.toObject(Group::class.java) ?: throw GroupNotFoundException()
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
            groupDoc.update(mapOf(
                GROUP_TITLE_FIELD to title,
            ))
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
                .firstOrNull()?.toObject(Group::class.java) ?: throw GroupNotFoundException()
            val user = group.members.firstOrNull { it.uid == userId } ?: throw UserNotFoundException()

            if (group.balance[userId].orZero() != ZERO)
                throw GroupBalanceException("User still have balance, please settle up him first")

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
                .firstOrNull()?.toObject(Group::class.java) ?: throw GroupNotFoundException()

            val invitedUser = group.members.firstOrNull { it.inviteCode == code } ?: throw UserNotFoundException()
            val firebaseUser = getCurrentUser()
            val joinedUser = invitedUser.copy(
                firebaseUserId = firebaseUser.uid,
                inviteCode = "",
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

    override suspend fun sendPayment(payment: Payment, group: Group): Group {
        return withContext(ioDispatcher) {
            if (payment.description.isEmpty()) throw PaymentInvalidException("Empty description")
            if (payment.value.isEmpty()) throw PaymentInvalidException("Empty value")

            val createdBy = group.findMemberByFirebaseId(getCurrentUser().uid)
                ?: throw PaymentInvalidException("Current user not found in group")

            val groupDoc = firestore.document("$GROUPS_REF/${group.id}")
            val total = payment.value.toBigDecimal().orZero().setScale(2, RoundingMode.CEILING)
            val shared = total.div(payment.paidTo.size.toBigDecimal()).setScale(2, RoundingMode.CEILING)

            // TODO: Send payments to be processed online to avoid balance divergences between members
            val balance = group.balance.toMutableMap()
            payment.paidTo.forEach { member ->
                balance[member.uid] = balance[member.uid]?.toBigDecimal().orZero().plus(shared).toString()
            }
            balance[payment.paidBy.uid] = balance[payment.paidBy.uid]?.toBigDecimal()?.minus(total).toString()

            groupDoc.update(mapOf(
                GROUP_PAYMENTS_FIELD to FieldValue.arrayUnion(payment.copy(createdBy = createdBy)),
                GROUP_BALANCE_FIELD to balance
            ))
            return@withContext getGroupById(group.id)
        }
    }

    private fun getCurrentUser() = firebaseAuth.currentUser ?: throw UserNotFoundException()

    private fun getUserGroupsQuery(): Query {
        val firebaseUser = getCurrentUser()
        return firestore.collection(GROUPS_REF)
            .whereArrayContains(GROUP_FIREBASE_MEMBERS_IDS_FIELD, firebaseUser.uid)
    }
}