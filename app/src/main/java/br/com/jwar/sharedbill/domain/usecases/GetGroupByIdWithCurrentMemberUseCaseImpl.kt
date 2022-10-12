package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.exceptions.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetGroupByIdWithCurrentMemberUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
): GetGroupByIdWithCurrentMemberUseCase {
    override suspend fun invoke(
        groupId: String,
        refresh: Boolean
    ): Flow<Resource<Pair<Group, User>>> {
        return userRepository.getUser().zip(groupRepository.getGroupById(groupId, refresh)) {
            userResource, groupResource -> Pair(userResource, groupResource)
        }.map {
            val (userResource, groupResource) = it
            when {
                userResource is Resource.Failure ->
                    Resource.Failure(userResource.throwable)
                groupResource is Resource.Failure ->
                    Resource.Failure(groupResource.throwable)
                userResource is Resource.Success && groupResource is Resource.Success ->
                    processSuccessResources(groupResource.data, userResource.data)
                else -> Resource.Loading
            }
        }
    }

    private fun processSuccessResources(group: Group, user: User) =
        group.findMemberByFirebaseId(user.firebaseUserId)?.let { currentMember ->
            Resource.Success(Pair(group, currentMember))
        }?: Resource.Failure(UserNotFoundException())
}