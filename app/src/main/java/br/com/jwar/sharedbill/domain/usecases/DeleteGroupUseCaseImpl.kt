package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.domain.account.repositories.UserRepository
import javax.inject.Inject

class DeleteGroupUseCaseImpl @Inject constructor(
    private val groupsRepository: GroupRepository,
    private val userRepository: UserRepository
) : DeleteGroupUseCase {
    override suspend fun invoke(groupId: String): Result<Unit> = resultOf {
        val group = groupsRepository.getGroupById(groupId)
        val currentUser = userRepository.getCurrentUser()

        if (currentUser.firebaseUserId != group.owner.firebaseUserId)
            throw GroupException.DeletingFromNonOwnerException

        groupsRepository.deleteGroup(groupId)
    }
}