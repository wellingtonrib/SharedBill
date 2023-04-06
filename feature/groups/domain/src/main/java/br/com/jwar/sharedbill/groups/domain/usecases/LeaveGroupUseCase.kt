package br.com.jwar.sharedbill.groups.domain.usecases

interface LeaveGroupUseCase {
    suspend operator fun invoke(groupId: String): Result<Unit?>
}