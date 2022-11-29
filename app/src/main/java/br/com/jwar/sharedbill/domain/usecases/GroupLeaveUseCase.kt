package br.com.jwar.sharedbill.domain.usecases

interface GroupLeaveUseCase {
    suspend operator fun invoke(groupId: String): Result<Unit?>
}