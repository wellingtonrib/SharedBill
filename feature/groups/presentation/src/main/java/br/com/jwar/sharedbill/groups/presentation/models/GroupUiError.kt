package br.com.jwar.sharedbill.groups.presentation.models

import br.com.jwar.sharedbill.account.domain.exceptions.UserException
import br.com.jwar.sharedbill.core.designsystem.util.UiText
import br.com.jwar.sharedbill.groups.domain.exceptions.GroupException
import br.com.jwar.sharedbill.groups.presentation.R

sealed class GroupUiError(val message: UiText) {

    object RemoveMemberWithNonZeroBalanceError : GroupUiError(
        UiText.StringResource(R.string.error_removing_member_with_non_zero_balance)
    )
    object DeletingFromNonOwnerError : GroupUiError(
        UiText.StringResource(R.string.error_deleting_group_from_non_owner)
    )
    object RemovingOwnerError : GroupUiError(
        UiText.StringResource(R.string.error_removing_owner_member)
    )
    object EmptyTitleError : GroupUiError(
        UiText.StringResource(R.string.error_group_invalid_title)
    )
    object InvalidUserNameError : GroupUiError(
        UiText.StringResource(R.string.error_invalid_user_name)
    )
    object GroupGenericError : GroupUiError(
        UiText.StringResource(R.string.error_generic)
    )
    object UserNotFoundException : GroupUiError(
        UiText.StringResource(R.string.error_unauthenticated_user)
    )
    object GroupJoinError : GroupUiError(UiText.StringResource(R.string.error_group_join))
    object GroupNotFoundError : GroupUiError(UiText.StringResource(R.string.error_group_not_found))
    object InvalidCodeError : GroupUiError(UiText.StringResource(R.string.error_invalid_invite_code))

    companion object {
        fun mapFrom(throwable: Throwable): GroupUiError {
            return when (throwable) {
                is GroupException.RemovingMemberWithNonZeroBalanceException -> RemoveMemberWithNonZeroBalanceError
                is GroupException.RemovingOwnerException -> RemovingOwnerError
                is GroupException.DeletingFromNonOwnerException -> DeletingFromNonOwnerError
                is GroupException.InvalidUserNameException -> InvalidUserNameError
                is GroupException.InvalidTitle -> EmptyTitleError
                is UserException.UserNotFoundException -> UserNotFoundException
                is GroupException.GroupJoinException -> GroupJoinError
                is GroupException.GroupNotFoundException -> GroupNotFoundError
                is GroupException.InvalidInviteCodeException -> InvalidCodeError
                else -> GroupGenericError
            }
        }
    }
}
