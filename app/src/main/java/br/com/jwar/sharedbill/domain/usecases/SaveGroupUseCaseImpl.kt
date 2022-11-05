package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class SaveGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : SaveGroupUseCase {
    override suspend fun invoke(groupId: String, title: String) =
        groupRepository.saveGroup(groupId, title)
}