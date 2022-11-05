package br.com.jwar.sharedbill.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.generic_components.Selectable
import java.util.UUID

class UserUiModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val inviteCode: String?
) : Selectable {

    companion object {
        fun sample() = UserUiModel(
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

    override val selectableLabel: String
        get() = name
}