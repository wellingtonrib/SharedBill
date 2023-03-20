package br.com.jwar.sharedbill.domain.account.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.domain.account.services.AuthService
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
): SignInUseCase {
    override suspend fun invoke() = resultOf {
        authRepository.signIn()
    }
}