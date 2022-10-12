package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupByIdUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GetGroupByIdUseCase {
    override suspend fun invoke(groupId: String, refresh: Boolean): Flow<Resource<Group>> =
        groupRepository.getGroupById(groupId, refresh)
}