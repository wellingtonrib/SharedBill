package br.com.jwar.sharedbill.groups.domain.usecases

interface RemoveMemberUseCase {
    suspend operator fun invoke(userId: String, groupId: String): Result<Unit>
}
