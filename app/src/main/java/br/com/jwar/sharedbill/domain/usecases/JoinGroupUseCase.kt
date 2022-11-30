package br.com.jwar.sharedbill.domain.usecases

interface JoinGroupUseCase {
    suspend operator fun invoke(inviteCode: String): Result<String>
}