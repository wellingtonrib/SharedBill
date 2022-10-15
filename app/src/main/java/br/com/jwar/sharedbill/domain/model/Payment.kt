package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Payment(
    val description: String = "",
    val value: String = "",
    val paidBy: User = User(),
    val paidTo: List<User> = emptyList(),
    val createdAt: Timestamp = Timestamp(Date()),
    val createdBy: User = User()
) : Parcelable