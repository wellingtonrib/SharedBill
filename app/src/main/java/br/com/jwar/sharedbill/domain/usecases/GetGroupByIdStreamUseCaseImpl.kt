package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.GroupRepository

class GetGroupByIdStreamUseCaseImpl(
    private val groupRepository: GroupRepository
) : GetGroupByIdStreamUseCase {
    override suspend fun invoke(groupId: String) =
        groupRepository.getGroupByIdStream(groupId)
}