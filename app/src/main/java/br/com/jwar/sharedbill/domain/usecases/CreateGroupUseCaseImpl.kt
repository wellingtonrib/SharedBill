package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupsRepository
) : CreateGroupUseCase {
    override suspend fun invoke(name: String): Flow<Resource<Group>> {
        val group = Group(title = name)
        return groupRepository.createGroup(group)
    }

}