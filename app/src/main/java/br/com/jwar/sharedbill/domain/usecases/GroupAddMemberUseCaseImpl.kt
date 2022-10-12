package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import java.util.*
import javax.inject.Inject

class GroupAddMemberUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): GroupAddMemberUseCase {
    override suspend fun invoke(userName: String, groupId: String) =
        groupRepository.addMember(
            user = User(
                uid = UUID.randomUUID().toString(),
                name = userName,
                inviteCode = User.generateCode(groupId)
            ),
            groupId = groupId
        )
}