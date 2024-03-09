package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import java.util.UUID
import javax.inject.Inject

class AddMemberUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val exceptionHandler: ExceptionHandler,
) : AddMemberUseCase {
    override suspend fun invoke(userName: String, groupId: String) = resultOf(exceptionHandler) {
        val userId = UUID.randomUUID().toString()
        groupRepository.addMember(
            user = User(
                id = userId,
                name = userName,
                inviteCode = User.generateCode(groupId)
            ),
            groupId = groupId
        )
        userId
    }
}
