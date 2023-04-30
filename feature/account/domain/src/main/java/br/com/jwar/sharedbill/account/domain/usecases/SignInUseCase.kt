package br.com.jwar.sharedbill.account.domain.usecases

import com.google.android.gms.auth.api.identity.BeginSignInResult

interface SignInUseCase {
    suspend operator fun invoke(): Result<BeginSignInResult>
}