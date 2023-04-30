package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.groups.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GetGroupsStreamUseCase {
    suspend operator fun invoke(): Flow<Result<List<Group>>>
}