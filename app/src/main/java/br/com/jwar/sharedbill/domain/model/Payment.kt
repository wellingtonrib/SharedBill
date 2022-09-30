package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class Payment(
    val description: String = "",
    val value: String = "",
    val paidBy: String = "",
    val paidTo: List<String> = emptyList(),
    val createdAt: Timestamp = Timestamp(Date())
) : Parcelable