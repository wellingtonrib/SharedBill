package br.com.jwar.sharedbill.account.domain.model


import androidx.annotation.Keep
import com.google.firebase.firestore.Exclude

@Keep
data class User(
    val id: String = "",
    val firebaseUserId: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val inviteCode: String? = null,
    @get:Exclude val isCurrentUser: Boolean = false
) {
    companion object {
        fun generateCode(groupId: String): String {
            val characters = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return characters.shuffled().takeLast(4).joinToString("") + groupId.takeLast(2).uppercase()
        }
    }
}