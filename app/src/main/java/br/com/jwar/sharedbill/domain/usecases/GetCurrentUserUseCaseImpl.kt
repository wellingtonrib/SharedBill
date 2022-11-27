package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.extensions.resultOf
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetCurrentUserUseCase {
    override suspend fun invoke() = resultOf {
        userRepository.getCurrentUser()
    }
}