package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GetGroupsStreamUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val exceptionHandler: ExceptionHandler
) : GetGroupsStreamUseCase {
    override suspend fun invoke(): Flow<Result<List<Group>>> = flow {
        try {
            groupRepository.getGroupsStream().collect { data ->
                emit(Result.success(data))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            exceptionHandler.recordException(e)
            emit(Result.failure(e))
        }
    }
}