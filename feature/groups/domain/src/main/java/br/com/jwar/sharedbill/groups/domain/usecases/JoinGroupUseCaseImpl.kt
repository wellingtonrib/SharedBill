package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import javax.inject.Inject

class JoinGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
): JoinGroupUseCase {
    override suspend fun invoke(inviteCode: String) = resultOf {
        val group = groupRepository.getGroupByInviteCode(inviteCode)
        val invitedUser = group.members.firstOrNull { it.inviteCode == inviteCode }
            ?: throw GroupException.InvalidInviteCodeException
        val currentUser = userRepository.getCurrentUser()
        val joinedUser = invitedUser.copy(
            firebaseUserId = currentUser.firebaseUserId,
            inviteCode = "",
            photoUrl = currentUser.photoUrl.toString(),
            email = currentUser.email
        )
        groupRepository.joinGroup(group.id, invitedUser, joinedUser); group.id
    }
}