package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import javax.inject.Inject

class LeaveGroupUseCaseImpl @Inject constructor(
    private val groupsRepository: GroupRepository,
    private val removeMemberUseCase: RemoveMemberUseCase
) : LeaveGroupUseCase {
    override suspend fun invoke(groupId: String) = resultOf {
        val group = groupsRepository.getGroupById(groupId)
        val currentUser = group.findCurrentUser()
            ?: throw GroupException.MemberNotFoundException

        removeMemberUseCase(currentUser.id, groupId).getOrNull()
    }
}
