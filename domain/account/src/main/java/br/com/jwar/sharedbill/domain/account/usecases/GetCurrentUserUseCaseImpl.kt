package br.com.jwar.sharedbill.domain.account.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.domain.account.repositories.UserRepository
import br.com.jwar.sharedbill.domain.account.usecases.GetCurrentUserUseCase
import javax.inject.Inject

class GetCurrentUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetCurrentUserUseCase {
    override suspend fun invoke() = resultOf {
        userRepository.getCurrentUser()
    }
}