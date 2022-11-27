package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group

interface UpdateGroupUseCase {
    suspend operator fun invoke(groupId: String, title: String): Result<Group>
}