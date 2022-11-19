package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    val groupId: String = "",
    val id: String = "",
    val description: String = "",
    val value: String = "",
    val paidBy: User = User(),
    val paidTo: List<User> = emptyList(),
    val createdAt: Timestamp = Timestamp(Date()),
    val createdBy: User = User(),
) : Parcelable