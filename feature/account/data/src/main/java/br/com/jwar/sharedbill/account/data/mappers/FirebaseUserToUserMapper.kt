package br.com.jwar.sharedbill.account.data.mappers

import br.com.jwar.sharedbill.account.domain.model.User
import com.google.firebase.auth.FirebaseUser

interface FirebaseUserToUserMapper {
    fun mapFrom(from: FirebaseUser): br.com.jwar.sharedbill.account.domain.model.User
}