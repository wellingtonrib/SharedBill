package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group

interface GroupJoinUseCase {
    suspend operator fun invoke(inviteCode: String): Result<Group>
}