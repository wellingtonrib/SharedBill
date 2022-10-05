package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface GroupJoinUseCase {
    suspend operator fun invoke(code: String): Flow<Resource<Group>>
}