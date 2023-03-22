package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import java.util.UUID
import javax.inject.Inject

class AddMemberUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): AddMemberUseCase {
    override suspend fun invoke(userName: String, groupId: String) = resultOf {
        if (userName.split(" ").count() == 1) throw GroupException.InvalidUserNameException
        groupRepository.addMember(
            user = User(
                id = UUID.randomUUID().toString(),
                name = userName,
                inviteCode = User.generateCode(groupId)
            ),
            groupId = groupId
        )
    }
}