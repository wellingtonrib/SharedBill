package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGroupsStreamUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GetGroupsStreamUseCase {
    override suspend fun invoke(): Flow<Result<List<Group>>> = flow {
        try {
            groupRepository.getGroupsStream().collect { data ->
                emit(Result.success(data))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}