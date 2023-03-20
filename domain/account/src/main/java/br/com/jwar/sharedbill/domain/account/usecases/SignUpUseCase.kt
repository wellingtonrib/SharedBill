package br.com.jwar.sharedbill.domain.account.usecases

import com.google.android.gms.auth.api.identity.BeginSignInResult

interface SignUpUseCase {
    suspend operator fun invoke(): Result<BeginSignInResult>
}