package br.com.jwar.sharedbill

import br.com.jwar.sharedbill.domain.account.model.User

object Fakes {

    val user = User(
        id = "123456",
        name = "User Fake",
        email = "userfake@domain.com",
        photoUrl = "www.domain.com/photos/userFake.jpg"
    )

}