package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.GroupRepository

class GetGroupByIdUseCaseImpl(
    private val groupRepository: GroupRepository
) : GetGroupByIdUseCase {
    override suspend fun invoke(groupId: String, refresh: Boolean) =
        groupRepository.getGroupById(groupId, refresh)
}