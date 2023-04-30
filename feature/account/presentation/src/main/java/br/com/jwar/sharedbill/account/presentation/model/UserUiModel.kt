package br.com.jwar.sharedbill.account.presentation.model

import br.com.jwar.sharedbill.core.designsystem.components.UserCardUiModel

class UserUiModel(
    val name: String = "",
    val email: String = "",
    val imageUrl: String = "",
) {

    companion object {
        fun sample() = UserUiModel(
            name = "User One",
            email = "user@email.com",
            imageUrl = "",
        )
    }

    fun toUserCardUiModel() =
        UserCardUiModel(
            name = this.name,
            email = this.email,
            imageUrl = this.imageUrl,
        )
}