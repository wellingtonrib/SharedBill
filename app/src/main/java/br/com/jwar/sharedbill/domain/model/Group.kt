package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import java.util.UUID
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

    fun findMemberByUid(uid: String) = members.firstOrNull { it.uid == uid }

    fun findMemberByFirebaseId(firebaseId: String) = members.firstOrNull { it.firebaseUserId == firebaseId }

    companion object {
        fun sample() = Group(
            id = UUID.randomUUID().toString(),
            title = "My Fake Group",
            owner = User(
                name = "Fake Owner"
            ),
            members = listOf(
                User(uid = "first", name = "Fake Member One"),
                User(uid = "second", name = "Fake Member Two"),
                User(uid = "third", name = "Fake Member Three"),
            ),
            balance = mapOf(
                "first" to "200",
                "second" to "-100",
                "third" to "-100",
            ),
            payments = listOf(
                Payment(description = "Expense One", paidBy = User(name = "Fake Member One"), value = "100", createdAt = Timestamp.now()),
                Payment(description = "Expense Two", paidBy = User(name = "Fake Member One"), value = "100", createdAt = Timestamp.now()),
                Payment(description = "Expense Three", paidBy = User(name = "Fake Member One"), value = "100", createdAt = Timestamp.now()),
            )
        )
    }
}
