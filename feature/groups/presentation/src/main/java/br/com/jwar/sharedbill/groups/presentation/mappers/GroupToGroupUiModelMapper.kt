package br.com.jwar.sharedbill.groups.presentation.mappers

import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel

interface GroupToGroupUiModelMapper {
    fun mapFrom(from: Group): GroupUiModel
}
