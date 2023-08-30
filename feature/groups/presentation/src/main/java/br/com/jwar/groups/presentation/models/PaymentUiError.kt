package br.com.jwar.groups.presentation.models

import br.com.jwar.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.groups.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.core.designsystem.util.UiText
import br.com.jwar.sharedbill.groups.presentation.R

sealed class PaymentUiError(val message: UiText) {

    object InvalidPaidByError: PaymentUiError(UiText.StringResource(R.string.error_payment_payer_not_in_group))
    object InvalidDescriptionError: PaymentUiError(UiText.StringResource(R.string.error_payment_description_empty))
    object InvalidValueError: PaymentUiError(UiText.StringResource(R.string.error_payment_value_empty))
    object InvalidDateError: PaymentUiError(UiText.StringResource(R.string.error_payment_date_empty))
    object InvalidPaidToError: PaymentUiError(UiText.StringResource(R.string.error_payment_empty_related_members))
    object GenericError: PaymentUiError(UiText.StringResource(R.string.error_generic))

    val errorHandler get() = when(this) {
        is InvalidDescriptionError -> PaymentContract.Field.DescriptionField::class
        is InvalidValueError -> PaymentContract.Field.ValueField::class
        is InvalidDateError -> PaymentContract.Field.DateField::class
        is InvalidPaidByError -> PaymentContract.Field.PaidByField::class
        is InvalidPaidToError -> PaymentContract.Field.PaidToField::class
        else -> null
    }

    companion object {
        fun mapFrom(throwable: Throwable) = when(throwable) {
            is PaymentException.InvalidDescriptionException -> InvalidDescriptionError
            is PaymentException.InvalidValueException -> InvalidValueError
            is PaymentException.InvalidDateException -> InvalidDateError
            is PaymentException.InvalidPaidByException -> InvalidPaidByError
            is PaymentException.InvalidPaidToException -> InvalidPaidToError
            else -> GenericError
        }
    }
}