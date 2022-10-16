package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import javax.inject.Inject

class UserToUserUiModelMapperImpl @Inject constructor(): UserToUserUiModelMapper {
    override fun mapFrom(from: User) =
        UserUiModel(
            uid = from.uid,
            name = from.name,
            firstName = from.name.split(" ").first(),
            inviteCode = from.inviteCode
        )
}