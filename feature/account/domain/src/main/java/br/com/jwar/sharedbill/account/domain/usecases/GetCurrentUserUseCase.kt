package br.com.jwar.sharedbill.account.domain.usecases

import br.com.jwar.sharedbill.account.domain.model.User

interface GetCurrentUserUseCase {
    suspend operator fun invoke(): Result<User>
}
