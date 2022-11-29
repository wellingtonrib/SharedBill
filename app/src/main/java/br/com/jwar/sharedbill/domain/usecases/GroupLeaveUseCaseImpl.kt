package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.extensions.resultOf
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class GroupLeaveUseCaseImpl @Inject constructor(
    private val groupsRepository: GroupRepository,
    private val removeMemberUseCase: GroupRemoveMemberUseCase
): GroupLeaveUseCase {
    override suspend fun invoke(groupId: String) = resultOf {
        val group = groupsRepository.getGroupById(groupId)
        val currentUser = group.findCurrentUser()
            ?: throw GroupException.MemberNotFoundException

        removeMemberUseCase(currentUser.id, groupId).getOrNull()
    }
}