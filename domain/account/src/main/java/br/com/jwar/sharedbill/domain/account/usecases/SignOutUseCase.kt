package br.com.jwar.sharedbill.domain.account.usecases

interface SignOutUseCase {
    suspend operator fun invoke(): Result<Unit>
}