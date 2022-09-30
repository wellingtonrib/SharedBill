package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllGroupsUseCaseImpl @Inject constructor(
    private val groupsRepository: GroupsRepository
) : GetAllGroupsUseCase {
    override suspend fun invoke(refresh: Boolean): Flow<Resource<List<Group>>> =
        groupsRepository.getAllGroups(refresh)
}