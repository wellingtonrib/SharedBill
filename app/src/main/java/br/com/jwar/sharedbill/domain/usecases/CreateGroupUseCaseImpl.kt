package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.extensions.resultOf
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import java.util.*
import javax.inject.Inject

class CreateGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : CreateGroupUseCase {
    override suspend fun invoke(title: String) = resultOf {
        if (title.isBlank()) throw GroupException.InvalidTitle

        val userResult = userRepository.getCurrentUser()
        val owner = userResult.copy(id = UUID.randomUUID().toString())
        val group = Group(
            title = title,
            owner = owner,
            members = listOf(owner),
            firebaseMembersIds = listOf(owner.firebaseUserId)
        )
        groupRepository.createGroup(group)
    }

}

