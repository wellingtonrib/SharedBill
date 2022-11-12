package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetGroupByIdUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GetGroupByIdUseCase {
    override suspend fun invoke(groupId: String, refresh: Boolean): Flow<Resource<Group>> =
        groupRepository.getGroupByIdFlow(groupId, refresh)
}