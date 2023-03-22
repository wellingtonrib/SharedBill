package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.presentation.models.GroupMemberUiModel

interface UserToGroupMemberUiModelMapper {
    fun mapFrom(from: User): GroupMemberUiModel
}