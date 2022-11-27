package br.com.jwar.sharedbill.domain.services

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult

interface AuthService {
    suspend fun signIn(): Result<BeginSignInResult>
    suspend fun signUp(): Result<BeginSignInResult>
    suspend fun signInFirebase(data: Intent?): Result<Boolean>
    suspend fun signOut()
}