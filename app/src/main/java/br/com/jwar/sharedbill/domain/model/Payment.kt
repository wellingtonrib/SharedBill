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
    val createdAt: Timestamp = Timestamp(Date())
) : Parcelable {
    fun getDetail() = with(StringBuilder()) {
        append("Description: $description\n")
        append("Value: ${value.toCurrency()}\n")
        append("Paid by: ${paidBy.name}\n")
        append("Paid to: ${paidTo.joinToString(", "){ it.name }}\n")
        append("Create at: ${createdAt.format(DATE_FORMAT_FULL)}")
        toString()
    }
}