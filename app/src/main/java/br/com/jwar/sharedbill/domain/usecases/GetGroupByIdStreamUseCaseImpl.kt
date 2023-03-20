package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetGroupByIdStreamUseCaseImpl(
    private val groupRepository: GroupRepository
) : GetGroupByIdStreamUseCase {
    override suspend fun invoke(groupId: String) =
        groupRepository.getGroupByIdStream(groupId)
            .map { resultOf { it } }
            .catch { emit(Result.failure(it)) }
}