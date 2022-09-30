package br.com.jwar.sharedbill.data.datasources

import br.com.jwar.sharedbill.data.mappers.DocumentSnapshotToGroupMapper
import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.domain.exceptions.GroupNotFoundException
import br.com.jwar.sharedbill.domain.exceptions.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
        const val GROUP_MEMBERS_IDS_FIELD = "membersIds"
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

    private fun getCurrentUser() = firebaseAuth.currentUser ?: throw UserNotFoundException()

    private fun getUserGroupsQuery(): Query {
        val firebaseUser = getCurrentUser()
        return firestore.collection(GROUPS_REF)
            .whereArrayContains(GROUP_MEMBERS_IDS_FIELD, firebaseUser.uid)
    }
}