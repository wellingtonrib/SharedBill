package br.com.jwar.groups.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.core.designsystem.components.Selectable
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.groups.presentation.R
import java.util.*

class GroupMemberUiModel(
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

    @Composable
    fun getJoinInfo() =
        if (inviteCode.isNullOrBlank()) stringResource(R.string.label_joined)
        else stringResource(R.string.message_invite_code, inviteCode)

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