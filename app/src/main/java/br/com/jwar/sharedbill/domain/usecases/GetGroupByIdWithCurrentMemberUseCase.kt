package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GetGroupByIdWithCurrentMemberUseCase {
    suspend operator fun invoke(groupId: String, refresh: Boolean = false):
            Flow<Resource<Pair<Group, User>>>
}