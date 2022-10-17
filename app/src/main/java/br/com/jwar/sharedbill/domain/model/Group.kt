package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class
Group(
    val id: String = "",
    val title: String = "",
    val owner: User = User(),
    val members: List<User> = emptyList(),
    val firebaseMembersIds: List<String> = emptyList(),
    val payments: List<Payment> = emptyList(),
    val balance: Map<String, String> = emptyMap()
) : Parcelable {

    fun findMemberByUid(uid: String) =
        members.firstOrNull { it.uid == uid }

    fun findMemberByFirebaseId(firebaseId: String) =
        members.firstOrNull { it.firebaseUserId == firebaseId }
}
