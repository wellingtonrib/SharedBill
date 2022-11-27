package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group

interface CreateGroupUseCase {
    suspend operator fun invoke(title: String): Result<Group>
}