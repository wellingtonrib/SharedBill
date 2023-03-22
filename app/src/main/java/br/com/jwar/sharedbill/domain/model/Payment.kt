package br.com.jwar.sharedbill.domain.model

import br.com.jwar.sharedbill.account.domain.model.User
import com.google.firebase.Timestamp
import java.util.*

data class Payment(
    val groupId: String = "",
    val id: String = "",
    val description: String = "",
    val value: String = "",
    val paidBy: User = User(),
    val paidTo: List<User> = emptyList(),
    val createdAt: Timestamp = Timestamp(Date()),
    val createdBy: User = User(),
)