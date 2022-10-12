package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class GroupRemoveMemberUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GroupRemoveMemberUseCase {
    override suspend fun invoke(userId: String, groupId: String) =
        groupRepository.removeMember(userId, groupId)
}