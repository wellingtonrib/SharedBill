package br.com.jwar.sharedbill.groups.presentation.mappers

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import javax.inject.Inject

class UserToGroupMemberUiModelMapperImpl @Inject constructor() : UserToGroupMemberUiModelMapper {
    override fun mapFrom(from: User) =
        GroupMemberUiModel(
            uid = from.id,
            name = from.name,
            email = from.email,
            imageUrl = from.photoUrl.orEmpty(),
            inviteCode = from.inviteCode,
            isCurrentUser = from.isCurrentUser,
        )
}
