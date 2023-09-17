package br.com.jwar.sharedbill.groups.presentation.mappers

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel

interface UserToGroupMemberUiModelMapper {
    fun mapFrom(from: User): GroupMemberUiModel
}