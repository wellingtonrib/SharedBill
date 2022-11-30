package br.com.jwar.sharedbill.presentation.models

import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.presentation.ui.util.UiText

sealed class GroupUiError(val message: UiText) {

    object RemoveMemberWithNonZeroBalanceError: GroupUiError(UiText.StringResource(R.string.error_removing_member_with_non_zero_balance))
    object DeletingFromNonOwnerError: GroupUiError(UiText.StringResource(R.string.error_deleting_group_from_non_owner))
    object RemovingOwnerError: GroupUiError(UiText.StringResource(R.string.error_removing_owner_member))
    object EmptyTitleError: GroupUiError(UiText.StringResource(R.string.error_group_invalid_title))
    object InvalidUserNameError: GroupUiError(UiText.StringResource(R.string.error_invalid_user_name))
    object GroupGenericError: GroupUiError(UiText.StringResource(R.string.error_generic))

    companion object {
        fun mapFrom(throwable: Throwable) = when(throwable) {
            is GroupException.RemovingMemberWithNonZeroBalanceException -> RemoveMemberWithNonZeroBalanceError
            is GroupException.RemovingOwnerException -> RemovingOwnerError
            is GroupException.DeletingFromNonOwnerException -> DeletingFromNonOwnerError
            is GroupException.InvalidUserNameException -> InvalidUserNameError
            is GroupException.InvalidTitle -> EmptyTitleError
            else -> GroupGenericError
        }
    }
}