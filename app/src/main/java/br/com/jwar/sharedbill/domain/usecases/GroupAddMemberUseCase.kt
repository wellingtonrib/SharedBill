package br.com.jwar.sharedbill.domain.usecases

interface GroupAddMemberUseCase {
    suspend operator fun invoke(userName: String, groupId: String): Result<Unit>
}