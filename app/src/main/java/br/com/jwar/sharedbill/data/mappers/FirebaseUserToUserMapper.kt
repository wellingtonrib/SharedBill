package br.com.jwar.sharedbill.data.mappers

import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.auth.FirebaseUser

interface FirebaseUserToUserMapper {
    fun mapFrom(from: FirebaseUser): User
}