package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.domain.account.model.User
import br.com.jwar.sharedbill.presentation.models.GroupMemberUiModel

interface UserToGroupMemberUiModelMapper {
    fun mapFrom(from: User): GroupMemberUiModel
}