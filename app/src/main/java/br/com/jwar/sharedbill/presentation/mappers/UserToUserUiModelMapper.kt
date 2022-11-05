package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.models.UserUiModel

interface UserToUserUiModelMapper {
    fun mapFrom(from: User): UserUiModel
}