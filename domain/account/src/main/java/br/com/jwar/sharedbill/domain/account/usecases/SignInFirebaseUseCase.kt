package br.com.jwar.sharedbill.domain.account.usecases

import android.content.Intent

interface SignInFirebaseUseCase {
    suspend operator fun invoke(data: Intent?): Result<Unit>
}