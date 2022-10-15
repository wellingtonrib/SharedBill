package br.com.jwar.sharedbill.presentation.models

import java.util.*

class UserUiModel(
    val uid: String = "",
    val name: String = "",
    val firstName: String = ""
) {

    companion object {
        fun sample() = UserUiModel(
            uid = UUID.randomUUID().toString(),
            name = "User One",
            firstName = "User",
        )
    }
}