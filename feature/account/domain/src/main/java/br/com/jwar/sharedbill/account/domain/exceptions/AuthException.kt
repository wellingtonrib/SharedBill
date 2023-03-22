package br.com.jwar.sharedbill.account.domain.exceptions

sealed class AuthException: Exception() {
    object SignInException: AuthException()
    object SignUpException: AuthException()
    object SignInFirebaseException: AuthException()
}
