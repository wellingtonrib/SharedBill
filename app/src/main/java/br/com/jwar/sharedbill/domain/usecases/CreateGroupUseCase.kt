package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result

interface CreateGroupUseCase {
    suspend operator fun invoke(title: String): Result<Group>
}