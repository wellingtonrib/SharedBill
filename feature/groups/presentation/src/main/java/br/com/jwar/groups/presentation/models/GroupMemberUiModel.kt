package br.com.jwar.groups.presentation.models

import br.com.jwar.sharedbill.core.designsystem.components.Selectable
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import java.util.UUID

data class GroupMemberUiModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val inviteCode: String? = null
) : Selectable {

    companion object {
        fun sample() = GroupMemberUiModel(
            uid = UUID.randomUUID().toString(),
            name = "User One",
            email = "user@email.com",
            imageUrl = "",
            inviteCode = "",
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
}