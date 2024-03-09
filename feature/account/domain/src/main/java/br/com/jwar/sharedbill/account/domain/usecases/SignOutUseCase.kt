package br.com.jwar.sharedbill.account.domain.usecases

interface SignOutUseCase {
    suspend operator fun invoke(): Result<Unit>
}
