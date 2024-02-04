package br.com.jwar.sharedbill.groups.domain.usecases


interface DeletePaymentUseCase {
    suspend operator fun invoke(
        paymentId: String,
        groupId: String,
        refundSuffix: String
    ): Result<Unit>
}