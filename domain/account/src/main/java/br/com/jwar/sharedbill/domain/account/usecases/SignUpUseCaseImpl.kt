package br.com.jwar.sharedbill.domain.account.usecases

import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.domain.account.services.AuthService
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
): SignUpUseCase {
    override suspend fun invoke() = resultOf {
        authRepository.signUp()
    }
}