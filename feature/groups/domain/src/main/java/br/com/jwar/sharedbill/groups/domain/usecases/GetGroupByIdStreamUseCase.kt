package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.groups.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GetGroupByIdStreamUseCase {
    suspend operator fun invoke(groupId: String): Flow<Result<Group>>
}
