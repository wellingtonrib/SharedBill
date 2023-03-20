package br.com.jwar.sharedbill.domain.account.usecases

import br.com.jwar.sharedbill.domain.account.model.User

interface GetCurrentUserUseCase {
    suspend operator fun invoke(): Result<User>
}