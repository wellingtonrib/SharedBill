package br.com.jwar.sharedbill.groups.presentation.models

import br.com.jwar.sharedbill.core.designsystem.util.UiText
import br.com.jwar.sharedbill.groups.domain.exceptions.PaymentException
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract

sealed class PaymentUiError(val message: UiText) {

    object InvalidPaidByError : PaymentUiError(
        UiText.StringResource(R.string.error_payment_payer_not_in_group)
    )
    object InvalidDescriptionError : PaymentUiError(
        UiText.StringResource(R.string.error_payment_description_empty)
    )
    object InvalidValueError : PaymentUiError(
        UiText.StringResource(R.string.error_payment_value_invalid)
    )
    object InvalidDateError : PaymentUiError(
        UiText.StringResource(R.string.error_payment_date_invalid)
    )
    object InvalidPaidToError : PaymentUiError(
        UiText.StringResource(R.string.error_payment_empty_related_members)
    )
    object InvalidGroupMemberSizeError : PaymentUiError(
        UiText.StringResource(R.string.error_payment_members_size_invalid)
    )
    object GenericError : PaymentUiError(UiText.StringResource(R.string.error_generic))

    val errorHandler get() = when (this) {
        is InvalidDescriptionError -> GroupPaymentContract.Field.DescriptionField::class
        is InvalidValueError -> GroupPaymentContract.Field.ValueField::class
        is InvalidDateError -> GroupPaymentContract.Field.DateField::class
        is InvalidPaidByError -> GroupPaymentContract.Field.PaidByField::class
        is InvalidPaidToError -> GroupPaymentContract.Field.PaidToField::class
        else -> null
    }

    companion object {
        fun mapFrom(throwable: Throwable) = when (throwable) {
            is PaymentException.InvalidDescriptionException -> InvalidDescriptionError
            is PaymentException.InvalidValueException -> InvalidValueError
            is PaymentException.InvalidDateException -> InvalidDateError
            is PaymentException.InvalidPaidByException -> InvalidPaidByError
            is PaymentException.InvalidPaidToException -> InvalidPaidToError
            is PaymentException.InvalidGroupMembersSize -> InvalidGroupMemberSizeError
            else -> GenericError
        }
    }
}
