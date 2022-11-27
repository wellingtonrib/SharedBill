package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.core.extensions.resultOf
import br.com.jwar.sharedbill.domain.services.AuthService
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
): SignInUseCase {
    override suspend fun invoke() = resultOf {
        authRepository.signIn()
    }
}