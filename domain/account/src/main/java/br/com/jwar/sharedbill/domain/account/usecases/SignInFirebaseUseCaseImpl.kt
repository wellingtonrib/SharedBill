package br.com.jwar.sharedbill.domain.account.usecases

import android.content.Intent
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import br.com.jwar.sharedbill.domain.account.services.AuthService
import javax.inject.Inject

class SignInFirebaseUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
): SignInFirebaseUseCase {
    override suspend fun invoke(data: Intent?) = resultOf {
        authRepository.signInFirebase(data)
    }
}