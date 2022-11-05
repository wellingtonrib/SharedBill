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
    val inviteCode: String? = null,
) : Parcelable {
    companion object {
        fun generateCode(groupId: String): String {
            val characters = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return characters.shuffled().takeLast(4).joinToString("") + groupId.takeLast(2)
        }
    }

    fun getFirstName() = name.split(" ").first()
}