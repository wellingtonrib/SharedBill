package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import javax.inject.Inject

class UpdateGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val exceptionHandler: ExceptionHandler,
) : UpdateGroupUseCase {
    override suspend fun invoke(groupId: String, title: String) = resultOf(exceptionHandler) {
        if (title.isBlank()) throw GroupException.InvalidTitle
        groupRepository.updateGroup(groupId, title)
    }
}
