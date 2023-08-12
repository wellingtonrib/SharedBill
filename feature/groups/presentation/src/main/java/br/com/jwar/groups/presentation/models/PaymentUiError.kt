package br.com.jwar.groups.presentation.models

import br.com.jwar.sharedbill.groups.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.core.designsystem.util.UiText
import br.com.jwar.sharedbill.groups.presentation.R

sealed class PaymentUiError(val message: UiText) {

    object CurrentUserNotInGroupError: PaymentUiError(UiText.StringResource(R.string.error_payment_user_not_in_group))
    object InvalidPaidByError: PaymentUiError(UiText.StringResource(R.string.error_payment_payer_not_in_group))
    object InvalidDescriptionError: PaymentUiError(UiText.StringResource(R.string.error_payment_description_empty))
    object InvalidValueError: PaymentUiError(UiText.StringResource(R.string.error_payment_value_empty))
    object InvalidDateError: PaymentUiError(UiText.StringResource(R.string.error_payment_date_empty))
    object InvalidPaidToError: PaymentUiError(UiText.StringResource(R.string.error_payment_empty_related_members))
    object PaymentGenericError: PaymentUiError(UiText.StringResource(R.string.error_generic))

    companion object {
        fun mapFrom(throwable: Throwable) = when(throwable) {
            is PaymentException.CurrentUserNotInGroupException -> CurrentUserNotInGroupError
            is PaymentException.PayerNotInGroupException -> InvalidPaidByError
            is PaymentException.EmptyDescriptionException -> InvalidDescriptionError
            is PaymentException.InvalidValueException -> InvalidValueError
            is PaymentException.EmptyDateException -> InvalidDateError
            is PaymentException.EmptyRelatedMembersException -> InvalidPaidToError
            else -> PaymentGenericError
        }
    }
}