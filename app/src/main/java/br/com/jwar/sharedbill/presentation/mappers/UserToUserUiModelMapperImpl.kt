package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import javax.inject.Inject

class UserToUserUiModelMapperImpl @Inject constructor(): UserToUserUiModelMapper {
    override fun mapFrom(from: User) =
        UserUiModel(
            uid = from.id,
            name = from.name,
            email = from.email,
            imageUrl = from.photoUrl.orEmpty(),
            inviteCode = from.inviteCode
        )
}