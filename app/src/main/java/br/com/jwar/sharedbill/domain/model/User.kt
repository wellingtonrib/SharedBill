package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import br.com.jwar.sharedbill.presentation.ui.generic_components.Selectable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class User(
    val uid: String = "",
    val firebaseUserId: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val inviteCode: String? = null,
) : Parcelable, Selectable {
    companion object {
        fun generateCode(groupId: String): String {
            val characters = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return characters.shuffled().takeLast(4).joinToString("") + groupId.takeLast(2)
        }
    }

    @get:Exclude
    val joinInfo: String
        get() = if (inviteCode.isNullOrBlank()) "Joined" else "Invite code: $inviteCode"

    @get:Exclude
    override val selectableLabel: String
        get() = name
}