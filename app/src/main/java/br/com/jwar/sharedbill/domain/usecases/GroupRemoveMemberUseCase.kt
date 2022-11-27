package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group

interface GroupRemoveMemberUseCase {
    suspend operator fun invoke(userId: String, groupId: String): Result<Group>
}