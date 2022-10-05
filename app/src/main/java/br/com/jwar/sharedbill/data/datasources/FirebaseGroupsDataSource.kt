package br.com.jwar.sharedbill.data.datasources

import br.com.jwar.sharedbill.data.mappers.DocumentSnapshotToGroupMapper
import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.exceptions.GroupNotFoundException
import br.com.jwar.sharedbill.domain.exceptions.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class FirebaseGroupsDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val documentSnapshotToGroupMapper: DocumentSnapshotToGroupMapper,
    private val firebaseUserToUserMapper: FirebaseUserToUserMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GroupsDataSource {

    companion object {
        const val GROUPS_REF = "groups"
        const val GROUP_ID_FIELD = "id"
        const val GROUP_MEMBERS_FIELD = "members"
        const val GROUP_MEMBERS_IDS_FIELD = "membersIds"
        const val GROUP_INVITES_FIELD = "invites"
    }

    override suspend fun getGroupById(groupId: String): Group {
        return withContext(ioDispatcher) {
            val snapshot = getUserGroupsQuery()
                .whereEqualTo(GROUP_ID_FIELD, groupId)
                .get()
                .await()
                .documents
                .firstOrNull() ?: throw GroupNotFoundException()
            documentSnapshotToGroupMapper.mapFrom(snapshot)
        }
    }

    override suspend fun getAllGroups(): List<Group> {
        return withContext(ioDispatcher) {
            val snapshot = getUserGroupsQuery()
                .get()
                .await()
                .documents
            snapshot.map { documentSnapshotToGroupMapper.mapFrom(it) }
        }
    }

    override suspend fun createGroup(group: Group): Group {
        val firebaseUser = getCurrentUser()
        return withContext(ioDispatcher) {
            val groupDoc = firestore.collection(GROUPS_REF).document()
            val user = firebaseUserToUserMapper.mapFrom(firebaseUser)
            val newGroup = group.copy(
                id = groupDoc.id,
                owner = user,
                members = listOf(user),
                membersIds = listOf(user.uid)
            )
            groupDoc.set(newGroup); newGroup
        }
    }

    override suspend fun addMember(userName: String, groupId: String): Group {
        return withContext(ioDispatcher) {
            val newMember = User(
                uid = UUID.randomUUID().toString(),
                name = userName,
                inviteCode = User.generateCode()
            )
            val groupDoc = firestore.document("$GROUPS_REF/$groupId")
            groupDoc.update(GROUP_MEMBERS_FIELD, FieldValue.arrayUnion(newMember))
            groupDoc.update(GROUP_INVITES_FIELD, FieldValue.arrayUnion(newMember.inviteCode))
            return@withContext getGroupById(groupId)
        }
    }

    override suspend fun removeMember(userId: String, groupId: String): Group {
        TODO("Not implemented yet")
    }

    override suspend fun joinGroup(code: String) : Group {
        return withContext(ioDispatcher) {
            val snapshot = firestore.collection(GROUPS_REF)
                .whereArrayContains(GROUP_INVITES_FIELD, code)
                .get()
                .await()
                .documents
                .firstOrNull() ?: throw GroupNotFoundException()
            val group = documentSnapshotToGroupMapper.mapFrom(snapshot)
            val invitedUser = group.members.firstOrNull { it.inviteCode == code }

            if (invitedUser != null) {
                val firebaseUser = getCurrentUser()
                val invitedUserUpdated = invitedUser.copy(
                    firebaseUserId = firebaseUser.uid
                )
                val groupDoc = firestore.document("$GROUPS_REF/${group.id}")
                groupDoc.update(GROUP_MEMBERS_FIELD, FieldValue.arrayRemove(invitedUser))
                groupDoc.update(GROUP_MEMBERS_FIELD, FieldValue.arrayUnion(invitedUserUpdated))
                groupDoc.update(GROUP_MEMBERS_IDS_FIELD, FieldValue.arrayUnion(firebaseUser.uid))

            }
            return@withContext getGroupById(group.id)
        }
    }

    private fun getCurrentUser() = firebaseAuth.currentUser ?: throw UserNotFoundException()

    private fun getUserGroupsQuery(): Query {
        val firebaseUser = getCurrentUser()
        return firestore.collection(GROUPS_REF)
            .whereArrayContains(GROUP_MEMBERS_IDS_FIELD, firebaseUser.uid)
    }
}