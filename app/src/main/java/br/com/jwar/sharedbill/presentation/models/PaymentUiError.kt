package br.com.jwar.sharedbill.presentation.models

import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.presentation.ui.util.UiText

sealed class PaymentUiError(val message: UiText) {

    object CurrentUserNotInGroupError: PaymentUiError(UiText.StringResource(R.string.error_payment_user_not_in_group))
    object PayerNotInGroupError: PaymentUiError(UiText.StringResource(R.string.error_payment_payer_not_in_group))
    object EmptyDescriptionError: PaymentUiError(UiText.StringResource(R.string.error_payment_description_empty))
    object EmptyValueError: PaymentUiError(UiText.StringResource(R.string.error_payment_value_empty))
    object EmptyDateError: PaymentUiError(UiText.StringResource(R.string.error_payment_date_empty))
    object EmptyRelatedMembersError: PaymentUiError(UiText.StringResource(R.string.error_payment_empty_related_members))
    object PaymentGenericError: PaymentUiError(UiText.StringResource(R.string.error_generic))

    companion object {
        fun mapFrom(throwable: Throwable) = when(throwable) {
            is PaymentException.CurrentUserNotInGroupException -> CurrentUserNotInGroupError
            is PaymentException.PayerNotInGroupException -> PayerNotInGroupError
            is PaymentException.EmptyDescriptionException -> EmptyDescriptionError
            is PaymentException.EmptyValueException -> EmptyValueError
            is PaymentException.EmptyDateException -> EmptyDateError
            is PaymentException.EmptyRelatedMembersException -> EmptyRelatedMembersError
            else -> PaymentGenericError
        }
    }
}