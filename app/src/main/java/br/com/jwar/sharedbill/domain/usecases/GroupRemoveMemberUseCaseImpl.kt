package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.extensions.ZERO
import br.com.jwar.sharedbill.core.extensions.orZero
import br.com.jwar.sharedbill.core.extensions.resultOf
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class GroupRemoveMemberUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GroupRemoveMemberUseCase {
    override suspend fun invoke(userId: String, groupId: String) = resultOf {
        val group = groupRepository.getGroupById(groupId, true)
        val member = group.findMemberById(userId)
            ?: throw GroupException.MemberNotFoundException
        if (group.owner.id == userId)
            throw GroupException.RemovingOwnerException
        if (group.balance[userId].orZero() != ZERO)
            throw GroupException.RemovingMemberWithNonZeroBalanceException

        groupRepository.removeMember(member, groupId)
    }
}