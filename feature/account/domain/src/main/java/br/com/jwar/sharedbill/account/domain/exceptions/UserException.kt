package br.com.jwar.sharedbill.account.domain.exceptions

sealed class UserException : Exception() {
    object UserNotFoundException : UserException()
}
