package br.com.jwar.sharedbill.testing

import br.com.jwar.sharedbill.account.domain.model.User

object Fakes {
    fun makeUser() = User(
        id = "123456",
        name = "User Fake",
        email = "userfake@domain.com",
        photoUrl = "www.domain.com/photos/userFake.jpg"
    )
}