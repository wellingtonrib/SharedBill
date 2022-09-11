package br.com.jwar.sharedbill.domain.usecases

import android.content.Intent
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.services.AuthService
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class SignInFirebaseUseCaseImpl @Inject constructor(
    private val authRepository: AuthService
): SignInFirebaseUseCase {
    override suspend fun invoke(data: Intent?): Flow<Resource<Boolean>> =
        authRepository.signInFirebase(data)
}