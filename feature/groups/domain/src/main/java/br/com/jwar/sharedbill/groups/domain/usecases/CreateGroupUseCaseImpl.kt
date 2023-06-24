package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import java.util.UUID
import javax.inject.Inject

class CreateGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : CreateGroupUseCase {
    override suspend fun invoke(title: String) = resultOf {
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

