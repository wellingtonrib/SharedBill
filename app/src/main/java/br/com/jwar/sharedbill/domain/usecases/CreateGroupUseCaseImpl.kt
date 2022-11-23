package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.domain.exceptions.UserException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import java.util.*
import javax.inject.Inject

class CreateGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : CreateGroupUseCase {
    override suspend fun invoke(title: String): Result<Group> {
        if (title.isBlank()) return Result.Error(GroupException.InvalidTitle)
        val userResult = userRepository.getUser().getOrNull()
            ?: return Result.Error(UserException.UserNotFoundException)
        val owner = userResult.copy(id = UUID.randomUUID().toString())
        val group = Group(
            title = title,
            owner = owner,
            members = listOf(owner),
            firebaseMembersIds = listOf(owner.firebaseUserId)
        )
        return groupRepository.createGroup(group)
    }

}

