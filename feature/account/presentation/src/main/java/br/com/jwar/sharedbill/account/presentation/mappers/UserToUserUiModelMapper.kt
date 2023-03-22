package br.com.jwar.sharedbill.account.presentation.mappers

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.presentation.model.UserUiModel

interface UserToUserUiModelMapper {
    fun mapFrom(from: br.com.jwar.sharedbill.account.domain.model.User): UserUiModel
}