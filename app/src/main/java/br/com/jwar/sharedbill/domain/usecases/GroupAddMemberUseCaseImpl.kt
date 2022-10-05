package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import javax.inject.Inject

class GroupAddMemberUseCaseImpl @Inject constructor(
    private val groupsRepository: GroupsRepository
): GroupAddMemberUseCase {
    override suspend fun invoke(userName: String, groupId: String) =
        groupsRepository.addMember(userName, groupId)
}