package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGroupsUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GetAllGroupsUseCase {
    override suspend fun invoke(refresh: Boolean): Flow<Resource<List<Group>>> =
        groupRepository.getAllGroups(refresh)
}