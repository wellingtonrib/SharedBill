package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class
Group(
    val id: String = "",
    val title: String = "",
    val owner: User = User(),
    val members: List<User> = emptyList(),
    val membersIds: List<String> = emptyList(),
    val payments: List<Payment> = emptyList(),
    val balance: Map<String, String> = emptyMap()
) : Parcelable {
    companion object {
        fun fake() = Group(
            id = UUID.randomUUID().toString(),
            title = "My Fake Group",
            owner = User(
                name = "Fake Owner"
            ),
            members = listOf(
                User(name = "Fake Member One"),
                User(name = "Fake Member Two"),
                User(name = "Fake Member Three"),
            ),
            payments = listOf(
                Payment(description = "Expense One", paidBy = "User One", value = "100", createdAt = Timestamp.now()),
                Payment(description = "Expense Two", paidBy = "User One", value = "100", createdAt = Timestamp.now()),
                Payment(description = "Expense Three", paidBy = "User One", value = "100", createdAt = Timestamp.now()),
                Payment(description = "Expense Four", paidBy = "User One", value = "100", createdAt = Timestamp.now())
            )
        )
    }
}
