package br.com.jwar.sharedbill.ui.account.mappers

import br.com.jwar.sharedbill.domain.account.model.User
import br.com.jwar.sharedbill.ui.account.model.UserUiModel
import javax.inject.Inject

class UserToUserUiModelMapperImpl @Inject constructor(): UserToUserUiModelMapper {
    override fun mapFrom(from: User) =
        UserUiModel(
            name = from.name,
            email = from.email,
            imageUrl = from.photoUrl.orEmpty()
        )
}