package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.repositories.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetUserUseCase {
    override suspend fun invoke() = userRepository.getUser()
}