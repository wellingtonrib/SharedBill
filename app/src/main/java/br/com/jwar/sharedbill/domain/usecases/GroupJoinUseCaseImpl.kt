package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class GroupJoinUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): GroupJoinUseCase {
    override suspend fun invoke(code: String) =
        groupRepository.joinGroup(code)
}