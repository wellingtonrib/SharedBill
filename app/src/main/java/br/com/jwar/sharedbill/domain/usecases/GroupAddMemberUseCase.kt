package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface GroupAddMemberUseCase {
    suspend operator fun invoke(userName: String, group: Group): Flow<Resource<Group>>
}