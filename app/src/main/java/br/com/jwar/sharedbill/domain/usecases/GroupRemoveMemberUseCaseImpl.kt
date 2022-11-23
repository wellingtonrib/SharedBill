package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.ZERO
import br.com.jwar.sharedbill.core.orZero
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class GroupRemoveMemberUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GroupRemoveMemberUseCase {
    override suspend fun invoke(userId: String, groupId: String): Result<Group> {
        val group = groupRepository.getGroupById(groupId, true).getOrNull()
            ?: return Result.Error(GroupException.GroupNotFoundException)
        val member = group.findMemberById(userId)
            ?: return Result.Error(GroupException.MemberNotFoundException)
        if (group.balance[userId].orZero() != ZERO)
            return Result.Error(GroupException.RemoveMemberWithNonZeroBalanceException)
        return groupRepository.removeMember(member, groupId)
    }
}