package br.com.jwar.sharedbill.groups.presentation.mappers

import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.domain.model.Group

interface GroupToGroupUiModelMapper {
    fun mapFrom(from: Group): GroupUiModel
}