package br.com.jwar.sharedbill.data.mappers

import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.auth.FirebaseUser
import javax.inject.Singleton

@Singleton
class FirebaseUserToUserMapperImpl : FirebaseUserToUserMapper {
    override fun mapFrom(from: FirebaseUser) = with(from) {
        User(
            firebaseUserId = uid,
            name = displayName.orEmpty(),
            email =  email.orEmpty(),
            photoUrl = photoUrl?.toString()
        )
    }
}