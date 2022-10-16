package br.com.jwar.sharedbill.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import java.util.*

class UserUiModel(
    val uid: String = "",
    val name: String = "",
    val firstName: String = "",
    val inviteCode: String?
) {

    companion object {
        fun sample() = UserUiModel(
            uid = UUID.randomUUID().toString(),
            name = "User One",
            firstName = "User",
            inviteCode = "",
        )
    }

    @Composable
    fun getJoinInfo() =
        if (inviteCode.isNullOrBlank()) stringResource(R.string.label_joined)
        else stringResource(R.string.message_invite_code)
}