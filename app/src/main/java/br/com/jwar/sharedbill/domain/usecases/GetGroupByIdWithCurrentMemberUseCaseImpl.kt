package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class GetGroupByIdWithCurrentMemberUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
): GetGroupByIdWithCurrentMemberUseCase {
    override suspend fun invoke(
        groupId: String,
        refresh: Boolean
    ): Flow<Resource<Pair<Group, User>>> {
        return userRepository.getUser().combine(groupRepository.getGroupById(groupId, refresh)) {
            userResource, groupResource -> Pair(userResource, groupResource)
        }.map {
            val (userResource, groupResource) = it
            when {
                userResource is Resource.Failure ->
                    processFailureResources(userResource.throwable)
                groupResource is Resource.Failure ->
                    processFailureResources(groupResource.throwable)
                userResource is Resource.Success && groupResource is Resource.Success ->
                    processSuccessResources(groupResource.data, userResource.data)
                else -> Resource.Loading
            }
        }
    }

    private fun processFailureResources(throwable: Throwable) =
        Resource.Failure(throwable)

    private fun processSuccessResources(group: Group, user: User) =
        group.findMemberByFirebaseId(user.firebaseUserId)?.let { currentMember ->
            Resource.Success(Pair(group, currentMember))
        }?: Resource.Failure(UserNotFoundException)
}