package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Resource
import com.google.android.gms.auth.api.identity.BeginSignInResult
import kotlinx.coroutines.flow.Flow

interface SignInUseCase {
    suspend operator fun invoke(): Flow<Resource<BeginSignInResult>>
}