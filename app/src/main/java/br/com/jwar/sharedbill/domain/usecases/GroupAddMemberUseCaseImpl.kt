package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.extensions.resultOf
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import java.util.UUID
import javax.inject.Inject

class GroupAddMemberUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): GroupAddMemberUseCase {
    override suspend fun invoke(userName: String, groupId: String) = resultOf {
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