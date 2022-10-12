package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import br.com.jwar.sharedbill.core.DATE_FORMAT_FULL
import br.com.jwar.sharedbill.core.format
import br.com.jwar.sharedbill.core.toCurrency
import com.google.firebase.Timestamp
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    val description: String = "",
    val value: String = "",
    val paidBy: User = User(),
    val paidTo: List<User> = emptyList(),
    val createdAt: Timestamp = Timestamp(Date()),
    val createdBy: User = User()
) : Parcelable {
    fun getDetail() = with(StringBuilder()) {
        append("Description: $description\n")
        append("Value: ${value.toCurrency()}\n")
        append("Paid by: ${paidBy.firstName}\n")
        append("Paid to: ${paidTo.joinToString(", "){ it.firstName }}\n")
        append("Created by: ${createdBy.firstName}\n")
        append("Created at: ${createdAt.format(DATE_FORMAT_FULL)}")
        toString()
    }

    fun paidToDescription(group: Group) =
        if (paidTo.size == group.members.size) "All"
        else paidTo.joinToString(", ") { it.firstName }
}