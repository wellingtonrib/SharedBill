package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.User

interface GetCurrentUserUseCase {
    suspend operator fun invoke(): Result<User>
}