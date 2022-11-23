package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result

interface GetGroupByIdUseCase {
    suspend operator fun invoke(groupId: String, refresh: Boolean = false): Result<Group>
}