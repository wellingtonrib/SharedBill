package br.com.jwar.sharedbill.presentation.models

import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.domain.exceptions.GroupException
import br.com.jwar.sharedbill.presentation.ui.util.UiText

sealed class GroupEditingUiError(val message: UiText) {

    object RemoveMemberWithNonZeroBalanceError: GroupEditingUiError(UiText.StringResource(R.string.error_removing_member_with_non_zero_balance))
    object GroupGenericError: GroupEditingUiError(UiText.StringResource(R.string.error_generic))

    companion object {
        fun mapFrom(throwable: Throwable) = when(throwable) {
            is GroupException.RemoveMemberWithNonZeroBalanceException -> RemoveMemberWithNonZeroBalanceError
            else -> GroupGenericError
        }
    }
}