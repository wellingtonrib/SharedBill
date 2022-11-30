package br.com.jwar.sharedbill.domain.usecases

interface LeaveGroupUseCase {
    suspend operator fun invoke(groupId: String): Result<Unit?>
}