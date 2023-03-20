package br.com.jwar.sharedbill.domain.account.exceptions

sealed class UserException: Exception() {
    object UserNotFoundException: UserException()
}
