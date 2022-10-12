package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class SaveGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : SaveGroupUseCase {
    override suspend fun invoke(group: Group) =
        groupRepository.saveGroup(group)
}