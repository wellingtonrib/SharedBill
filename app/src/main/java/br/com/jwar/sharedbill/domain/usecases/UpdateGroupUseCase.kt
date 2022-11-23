package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result

interface UpdateGroupUseCase {
    suspend operator fun invoke(groupId: String, title: String): Result<Group>
}