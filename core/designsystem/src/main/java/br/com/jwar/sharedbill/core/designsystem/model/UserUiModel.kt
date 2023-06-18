package br.com.jwar.sharedbill.core.designsystem.model

import java.util.UUID

class UserUiModel(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val imageUrl: String = "",
) {
    companion object {
        fun sample() = UserUiModel(
            id = UUID.randomUUID().toString(),
            name = "User One",
            email = "user@email.com",
            imageUrl = "",
        )
    }
}