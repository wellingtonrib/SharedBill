package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.extensions.ZERO
import br.com.jwar.sharedbill.core.extensions.orZero
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class RemoveMemberUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : RemoveMemberUseCase {
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