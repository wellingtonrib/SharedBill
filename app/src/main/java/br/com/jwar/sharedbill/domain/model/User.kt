package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String = "",
    val firebaseUserId: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val inviteCode: String? = null
) : Parcelable {
    companion object {
        fun generateCode(): String {
            val characters = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..4).map { characters.random() }.joinToString("")
        }
    }
}