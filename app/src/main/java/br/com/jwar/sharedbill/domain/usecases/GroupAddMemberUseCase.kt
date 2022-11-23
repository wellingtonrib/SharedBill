package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result

interface GroupAddMemberUseCase {
    suspend operator fun invoke(userName: String, groupId: String): Result<Group>
}