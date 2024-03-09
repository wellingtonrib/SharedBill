package br.com.jwar.sharedbill.account.presentation.mappers

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import javax.inject.Inject

class UserToUserUiModelMapperImpl @Inject constructor() : UserToUserUiModelMapper {
    override fun mapFrom(from: User) =
        UserUiModel(
            name = from.name,
            email = from.email,
            imageUrl = from.photoUrl.orEmpty()
        )
}
