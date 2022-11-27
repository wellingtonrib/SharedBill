package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.User

interface GetUserUseCase {
    suspend operator fun invoke(): Result<User>
}