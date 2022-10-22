package br.com.jwar.sharedbill.domain.exceptions

sealed class PaymentException : Exception() {
    object PayerNotInGroupException: PaymentException()
    object CurrentUserNotInGroupException: PaymentException()
    object EmptyDescriptionException: PaymentException()
    object EmptyValueException: PaymentException()
    object EmptyDateException: PaymentException()
    object EmptyRelatedMembersException: PaymentException()
}