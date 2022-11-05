package br.com.jwar.sharedbill.domain.exceptions

sealed class GroupException: Exception() {
    object GroupNotFoundException: GroupException()
    object RemoveMemberWithNonZeroBalanceException: GroupException()
}