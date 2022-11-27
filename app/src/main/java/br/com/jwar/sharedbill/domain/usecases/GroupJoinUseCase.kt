package br.com.jwar.sharedbill.domain.usecases

interface GroupJoinUseCase {
    suspend operator fun invoke(inviteCode: String): Result<String>
}