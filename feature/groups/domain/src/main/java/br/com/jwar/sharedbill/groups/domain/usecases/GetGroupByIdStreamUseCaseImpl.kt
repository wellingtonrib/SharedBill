package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetGroupByIdStreamUseCaseImpl(
    private val groupRepository: GroupRepository,
    private val exceptionHandler: ExceptionHandler,
) : GetGroupByIdStreamUseCase {
    override suspend fun invoke(groupId: String) =
        groupRepository.getGroupByIdStream(groupId)
            .map { resultOf(exceptionHandler) { it } }
            .catch { emit(Result.failure(it)) }
}