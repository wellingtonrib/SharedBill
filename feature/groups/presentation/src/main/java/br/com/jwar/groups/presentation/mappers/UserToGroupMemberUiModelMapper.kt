package br.com.jwar.groups.presentation.mappers

import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.account.domain.model.User

interface UserToGroupMemberUiModelMapper {
    fun mapFrom(from: User): GroupMemberUiModel
}