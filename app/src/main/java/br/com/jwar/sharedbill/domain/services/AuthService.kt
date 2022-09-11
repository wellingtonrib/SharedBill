package br.com.jwar.sharedbill.domain.services

import android.content.Intent
import br.com.jwar.sharedbill.domain.model.Resource
import com.google.android.gms.auth.api.identity.BeginSignInResult
import kotlinx.coroutines.flow.Flow

interface AuthService {
    suspend fun signIn(): Flow<Resource<BeginSignInResult>>
    suspend fun signUp(): Flow<Resource<BeginSignInResult>>
    suspend fun signInFirebase(data: Intent?): Flow<Resource<Boolean>>
    suspend fun signOut(): Flow<Resource<Boolean>>
}