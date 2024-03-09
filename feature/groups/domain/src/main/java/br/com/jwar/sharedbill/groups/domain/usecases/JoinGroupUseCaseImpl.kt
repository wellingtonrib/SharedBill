package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import javax.inject.Inject

class JoinGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val exceptionHandler: ExceptionHandler,
) : JoinGroupUseCase {
    override suspend fun invoke(inviteCode: String) = resultOf(exceptionHandler) {
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
        val joinResult = resultOf(exceptionHandler) { groupRepository.joinGroup(group.id, inviteCode, joinedUser) }
        when {
            joinResult.isSuccess -> group.id
            else -> throw GroupException.GroupJoinException
        }
    }
}
