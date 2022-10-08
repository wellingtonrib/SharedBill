package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CreateGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupsRepository
) : CreateGroupUseCase {
    override suspend fun invoke(title: String): Flow<Resource<Group>> {
        return groupRepository.createGroup(Group(title = title))
    }

}