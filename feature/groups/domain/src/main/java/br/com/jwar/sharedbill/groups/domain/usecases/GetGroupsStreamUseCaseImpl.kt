package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetGroupsStreamUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GetGroupsStreamUseCase {
    override suspend fun invoke() =
        groupRepository.getGroupsStream()
            .map { resultOf { it } }
            .catch { emit(Result.failure(it)) }
}