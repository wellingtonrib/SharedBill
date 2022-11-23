package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Result
import com.google.android.gms.auth.api.identity.BeginSignInResult

interface SignUpUseCase {
    suspend operator fun invoke(): Result<BeginSignInResult>
}