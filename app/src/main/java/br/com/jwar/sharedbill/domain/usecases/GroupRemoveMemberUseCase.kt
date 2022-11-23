package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result

interface GroupRemoveMemberUseCase {
    suspend operator fun invoke(userId: String, groupId: String): Result<Group>
}