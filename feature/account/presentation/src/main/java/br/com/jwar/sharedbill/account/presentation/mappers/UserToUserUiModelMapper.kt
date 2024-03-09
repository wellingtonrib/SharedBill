package br.com.jwar.sharedbill.account.presentation.mappers

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel

interface UserToUserUiModelMapper {
    fun mapFrom(from: User): UserUiModel
}
