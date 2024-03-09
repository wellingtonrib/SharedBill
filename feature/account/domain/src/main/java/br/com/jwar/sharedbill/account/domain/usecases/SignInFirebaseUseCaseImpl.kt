package br.com.jwar.sharedbill.account.domain.usecases

import android.content.Intent
import br.com.jwar.sharedbill.account.domain.services.AuthService
import br.com.jwar.sharedbill.core.utility.extensions.resultOf
import javax.inject.Inject

class SignInFirebaseUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
) : SignInFirebaseUseCase {
    override suspend fun invoke(data: Intent?) = resultOf {
        authRepository.signInFirebase(data)
    }
}
