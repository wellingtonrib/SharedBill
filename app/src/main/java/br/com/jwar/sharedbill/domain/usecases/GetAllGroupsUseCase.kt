package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface GetAllGroupsUseCase {
    suspend operator fun invoke(refresh: Boolean = false): Flow<Resource<List<Group>>>
}