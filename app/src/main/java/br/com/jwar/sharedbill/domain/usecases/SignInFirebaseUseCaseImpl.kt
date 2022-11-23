package br.com.jwar.sharedbill.domain.usecases

import android.content.Intent
import br.com.jwar.sharedbill.domain.services.AuthService
import javax.inject.Inject

class SignInFirebaseUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
): SignInFirebaseUseCase {
    override suspend fun invoke(data: Intent?) =
        authRepository.signInFirebase(data)
}