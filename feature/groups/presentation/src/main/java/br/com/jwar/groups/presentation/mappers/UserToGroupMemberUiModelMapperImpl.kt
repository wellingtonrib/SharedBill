package br.com.jwar.groups.presentation.mappers

import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.account.domain.model.User
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