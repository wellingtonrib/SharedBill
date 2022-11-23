package br.com.jwar.sharedbill.domain.usecases

import android.content.Intent
import br.com.jwar.sharedbill.domain.model.Result

interface SignInFirebaseUseCase {
    suspend operator fun invoke(data: Intent?): Result<Boolean>
}