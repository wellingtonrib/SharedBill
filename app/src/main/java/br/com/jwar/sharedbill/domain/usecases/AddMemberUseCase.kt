package br.com.jwar.sharedbill.domain.usecases

interface AddMemberUseCase {
    suspend operator fun invoke(userName: String, groupId: String): Result<Unit>
}