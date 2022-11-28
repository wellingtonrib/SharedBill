package br.com.jwar.sharedbill.presentation.models

import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.presentation.ui.util.UiText

sealed class GroupEditingUiError(val message: UiText) {

    object RemoveMemberWithNonZeroBalanceError: GroupEditingUiError(UiText.StringResource(R.string.error_removing_member_with_non_zero_balance))
    object EmptyTitleError: GroupEditingUiError(UiText.StringResource(R.string.error_group_invalid_title))
    object InvalidUserNameError: GroupEditingUiError(UiText.StringResource(R.string.error_invalid_user_name))
    object GroupGenericError: GroupEditingUiError(UiText.StringResource(R.string.error_generic))

    companion object {
        fun mapFrom(throwable: Throwable) = when(throwable) {
            is GroupException.RemoveMemberWithNonZeroBalanceException -> RemoveMemberWithNonZeroBalanceError
            is GroupException.InvalidUserNameException -> InvalidUserNameError
            is GroupException.InvalidTitle -> EmptyTitleError
            else -> GroupGenericError
        }
    }
}