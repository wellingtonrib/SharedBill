package br.com.jwar.sharedbill.groups.domain.exceptions

sealed class PaymentException : Exception() {
    object InvalidPaidByException : PaymentException()
    object InvalidDescriptionException : PaymentException()
    object InvalidValueException : PaymentException()
    object InvalidDateException : PaymentException()
    object InvalidPaidToException : PaymentException()
    object InvalidGroupMembersSize : PaymentException()
}
