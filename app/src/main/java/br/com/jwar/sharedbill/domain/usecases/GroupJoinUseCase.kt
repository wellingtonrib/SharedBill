package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result

interface GroupJoinUseCase {
    suspend operator fun invoke(inviteCode: String): Result<Group>
}