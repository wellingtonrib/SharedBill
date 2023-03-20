package br.com.jwar.sharedbill.ui.account.mappers

import br.com.jwar.sharedbill.domain.account.model.User
import br.com.jwar.sharedbill.ui.account.model.UserUiModel

interface UserToUserUiModelMapper {
    fun mapFrom(from: User): UserUiModel
}