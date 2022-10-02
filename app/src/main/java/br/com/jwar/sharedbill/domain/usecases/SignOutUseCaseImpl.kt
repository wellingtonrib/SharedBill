package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.services.AuthService
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
): SignOutUseCase {
    override suspend fun invoke() = authRepository.signOut()
}