package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.domain.account.model.User
import br.com.jwar.sharedbill.presentation.models.GroupMemberUiModel
import javax.inject.Inject

class UserToGroupMemberUiModelMapperImpl @Inject constructor(): UserToGroupMemberUiModelMapper {
    override fun mapFrom(from: User) =
        GroupMemberUiModel(
            uid = from.id,
            name = from.name,
            email = from.email,
            imageUrl = from.photoUrl.orEmpty(),
            inviteCode = from.inviteCode
        )
}