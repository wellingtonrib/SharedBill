package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository

class GetGroupByIdUseCaseImpl(
    private val groupRepository: GroupRepository,
    private val exceptionHandler: ExceptionHandler,
) : GetGroupByIdUseCase {
    override suspend fun invoke(groupId: String, refresh: Boolean) = resultOf(exceptionHandler) {
        groupRepository.getGroupById(groupId, refresh)
    }
}
