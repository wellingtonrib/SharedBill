package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.extensions.resultOf
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import javax.inject.Inject

class UpdateGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : UpdateGroupUseCase {
    override suspend fun invoke(groupId: String, title: String) = resultOf {
        groupRepository.updateGroup(groupId, title)
    }
}