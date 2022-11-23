package br.com.jwar.sharedbill.domain.exceptions

sealed class GroupException: Exception() {
    object GroupNotFoundException: GroupException()
    object MemberNotFoundException: GroupException()
    object InvalidCurrentUser: GroupException()
    object InvalidInviteCodeException: GroupException()
    object RemoveMemberWithNonZeroBalanceException: GroupException()
    object InvalidTitle : Exception()
}