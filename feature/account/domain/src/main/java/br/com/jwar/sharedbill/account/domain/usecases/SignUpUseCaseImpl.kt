package br.com.jwar.sharedbill.account.domain.usecases

import br.com.jwar.sharedbill.account.domain.services.AuthService
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
) : SignUpUseCase {
    override suspend fun invoke() = resultOf {
        authRepository.signUp()
    }
}
