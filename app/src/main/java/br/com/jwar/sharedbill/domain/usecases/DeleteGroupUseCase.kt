package br.com.jwar.sharedbill.domain.usecases

interface DeleteGroupUseCase {
    suspend operator fun invoke(groupId: String): Result<Unit>
}