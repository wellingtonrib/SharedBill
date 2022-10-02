package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.services.AuthService
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
): SignUpUseCase {
    override suspend fun invoke() = authRepository.signUp()
}