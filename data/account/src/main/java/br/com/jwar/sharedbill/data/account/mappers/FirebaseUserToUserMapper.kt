package br.com.jwar.sharedbill.data.account.mappers

import br.com.jwar.sharedbill.domain.account.model.User
import com.google.firebase.auth.FirebaseUser

interface FirebaseUserToUserMapper {
    fun mapFrom(from: FirebaseUser): User
}