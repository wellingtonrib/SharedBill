package br.com.jwar.sharedbill.domain.services

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult

interface AuthService {
    suspend fun signIn(): BeginSignInResult
    suspend fun signUp(): BeginSignInResult
    suspend fun signInFirebase(data: Intent?)
    suspend fun signOut()
}