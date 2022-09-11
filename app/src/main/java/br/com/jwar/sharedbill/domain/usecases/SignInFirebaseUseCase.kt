package br.com.jwar.sharedbill.domain.usecases

import android.content.Intent
import br.com.jwar.sharedbill.domain.model.Resource
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface SignInFirebaseUseCase {
    suspend operator fun invoke(data: Intent?): Flow<Resource<Boolean>>
}