package br.com.jwar.sharedbill.account.data.mappers

import br.com.jwar.sharedbill.account.domain.model.User
import com.google.firebase.auth.FirebaseUser
import javax.inject.Singleton

@Singleton
class FirebaseUserToUserMapperImpl : FirebaseUserToUserMapper {
    override fun mapFrom(from: FirebaseUser) = with(from) {
        br.com.jwar.sharedbill.account.domain.model.User(
            firebaseUserId = uid,
            name = displayName.orEmpty(),
            email = email.orEmpty(),
            photoUrl = photoUrl?.toString()
        )
    }
}