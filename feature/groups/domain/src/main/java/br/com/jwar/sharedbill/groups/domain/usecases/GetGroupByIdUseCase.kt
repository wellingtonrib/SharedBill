package br.com.jwar.sharedbill.groups.domain.usecases

import br.com.jwar.sharedbill.groups.domain.model.Group

interface GetGroupByIdUseCase {
    suspend operator fun invoke(groupId: String, refresh: Boolean = false): Result<Group>
}
