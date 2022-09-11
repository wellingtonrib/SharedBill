package br.com.jwar.sharedbill.data.mappers

import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.auth.FirebaseUser

class FirebaseUserToUserMapperImpl : FirebaseUserToUserMapper {

    override fun mapFrom(from: FirebaseUser) = with(from) {
        User(
            uid = uid,
            name = displayName.orEmpty(),
            email =  email.orEmpty(),
            photoUrl = photoUrl?.toString()
        )
    }
}