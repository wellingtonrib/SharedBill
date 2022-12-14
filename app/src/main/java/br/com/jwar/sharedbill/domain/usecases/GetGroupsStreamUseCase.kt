package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GetGroupsStreamUseCase {
    suspend operator fun invoke(): Flow<Result<List<Group>>>
}