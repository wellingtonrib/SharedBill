package br.com.jwar.sharedbill.domain.account.exceptions

sealed class AuthException: Exception() {
    object SignInException: AuthException()
    object SignUpException: AuthException()
    object SignInFirebaseException: AuthException()
}
