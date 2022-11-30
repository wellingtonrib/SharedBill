package br.com.jwar.sharedbill.domain.exceptions

sealed class GroupException: Exception() {
    object GroupNotFoundException: GroupException()
    object MemberNotFoundException: GroupException()
    object InvalidUserNameException: GroupException()
    object InvalidInviteCodeException: GroupException()
    object RemovingMemberWithNonZeroBalanceException: GroupException()
    object RemovingOwnerException: GroupException()
    object InvalidTitle : GroupException()
    object DeletingFromNonOwnerException : GroupException()
}