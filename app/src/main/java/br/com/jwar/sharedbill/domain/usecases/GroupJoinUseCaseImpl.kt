package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import javax.inject.Inject

class GroupJoinUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
): GroupJoinUseCase {
    override suspend fun invoke(inviteCode: String): Result<Group> {
        val group = groupRepository.getGroupByInviteCode(inviteCode).getOrNull()
            ?: return Result.failure(GroupException.GroupNotFoundException)
        val invitedUser = group.members.firstOrNull { it.inviteCode == inviteCode }
            ?: return Result.failure(GroupException.InvalidInviteCodeException)
        val currentUser = userRepository.getUser().getOrNull()
            ?: return Result.failure(GroupException.InvalidCurrentUser)
        val joinedUser = invitedUser.copy(
            firebaseUserId = currentUser.firebaseUserId,
            inviteCode = "",
            photoUrl = currentUser.photoUrl.toString(),
            email = currentUser.email
        )
        return groupRepository.joinGroup(group.id, invitedUser, joinedUser)
    }
}