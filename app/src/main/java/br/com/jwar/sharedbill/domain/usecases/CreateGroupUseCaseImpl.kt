package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupsRepository
) : CreateGroupUseCase {
    override suspend fun invoke(group: Group): Flow<Resource<Group>> {
        return groupRepository.createGroup(group)
    }

}