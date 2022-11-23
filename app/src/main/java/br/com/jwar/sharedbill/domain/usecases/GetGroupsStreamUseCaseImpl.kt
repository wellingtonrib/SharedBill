package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class GetGroupsStreamUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GetGroupsStreamUseCase {
    override suspend fun invoke() = groupRepository.getGroupsStream()
}