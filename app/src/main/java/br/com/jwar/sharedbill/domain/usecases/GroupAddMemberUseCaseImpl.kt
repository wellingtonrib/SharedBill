package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GroupAddMemberUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val groupsRepository: GroupsRepository
): GroupAddMemberUseCase {
    override suspend fun invoke(userName: String, group: Group): Flow<Resource<Group>> {
        val user = User(name = userName)
        // TODO: Receive groupId only and refresh page on back
        return combine(userRepository.createUser(user)) {
            // TODO: Add member to group for real
            val members = group.members
            Resource.Success(group.copy(members = members.toMutableList().apply {
                add(user)
            }))
        }
    }
}