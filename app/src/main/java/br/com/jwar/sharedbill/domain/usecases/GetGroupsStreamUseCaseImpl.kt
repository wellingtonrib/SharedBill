package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGroupsStreamUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GetGroupsStreamUseCase {
    override suspend fun invoke() =
        groupRepository.getGroupsStream()
            .map { resultOf { it } }
            .catch { emit(Result.failure(it)) }
}