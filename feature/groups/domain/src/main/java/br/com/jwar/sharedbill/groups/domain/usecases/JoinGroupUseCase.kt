package br.com.jwar.sharedbill.groups.domain.usecases

interface JoinGroupUseCase {
    suspend operator fun invoke(inviteCode: String): Result<String>
}