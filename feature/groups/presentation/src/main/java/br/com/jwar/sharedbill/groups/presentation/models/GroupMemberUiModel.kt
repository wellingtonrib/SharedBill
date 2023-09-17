package br.com.jwar.sharedbill.groups.presentation.models

import br.com.jwar.sharedbill.core.designsystem.components.Selectable
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import java.util.UUID

data class GroupMemberUiModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val inviteCode: String? = null,
    val isCurrentUser: Boolean = false,
) : Selectable {

    companion object {
        fun sample() = GroupMemberUiModel(
            uid = UUID.randomUUID().toString(),
            name = "User One",
            email = "user@email.com",
            imageUrl = "",
            inviteCode = "",
            isCurrentUser = true,
        )
    }

    fun toUserUiModel() =
        UserUiModel(
            id = this.uid,
            name = this.name,
            email = this.email,
            imageUrl = this.imageUrl,
        )

    override val selectableLabel: String
        get() = name

    override val selectableId: String
        get() = uid

    val firstName
        get() = name.split(" ").first()
}