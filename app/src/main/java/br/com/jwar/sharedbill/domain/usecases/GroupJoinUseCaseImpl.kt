package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import javax.inject.Inject

class GroupJoinUseCaseImpl @Inject constructor(
    private val groupsRepository: GroupsRepository
): GroupJoinUseCase {
    override suspend fun invoke(code: String) =
        groupsRepository.joinGroup(code)
}