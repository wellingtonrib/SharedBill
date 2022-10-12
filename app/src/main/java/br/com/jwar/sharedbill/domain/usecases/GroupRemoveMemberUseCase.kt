package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface GroupRemoveMemberUseCase {
    suspend operator fun invoke(userId: String, groupId: String): Flow<Resource<Group>>
}