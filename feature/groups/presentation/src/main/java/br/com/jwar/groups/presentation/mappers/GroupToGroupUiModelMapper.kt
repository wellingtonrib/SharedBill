package br.com.jwar.groups.presentation.mappers

import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.domain.model.Group

interface GroupToGroupUiModelMapper {
    fun mapFrom(from: Group): GroupUiModel
}