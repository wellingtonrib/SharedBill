package br.com.jwar.sharedbill.presentation.mappers

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.models.GroupUiModel

interface GroupToGroupUiModelMapper {
    fun mapFrom(from: Group): GroupUiModel
}