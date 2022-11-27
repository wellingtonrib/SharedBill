package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GetGroupByIdStreamUseCase {
    suspend operator fun invoke(groupId: String): Flow<Result<Group>>
}