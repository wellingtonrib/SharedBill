package br.com.jwar.sharedbill.domain.usecases

interface UpdateGroupUseCase {
    suspend operator fun invoke(groupId: String, title: String): Result<Unit>
}