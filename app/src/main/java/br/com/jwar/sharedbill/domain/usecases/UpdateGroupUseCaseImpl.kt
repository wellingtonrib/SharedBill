package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class UpdateGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : UpdateGroupUseCase {
    override suspend fun invoke(groupId: String, title: String) =
        groupRepository.updateGroup(groupId, title)
}