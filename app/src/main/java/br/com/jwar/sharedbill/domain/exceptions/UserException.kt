package br.com.jwar.sharedbill.domain.exceptions

sealed class UserException: Exception() {
    object UserNotFoundException: UserException()
}
