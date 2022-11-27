package br.com.jwar.sharedbill.domain.usecases

interface GroupRemoveMemberUseCase {
    suspend operator fun invoke(userId: String, groupId: String): Result<Unit>
}