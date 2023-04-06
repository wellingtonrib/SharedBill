package br.com.jwar.sharedbill.groups.domain.usecases

interface DeleteGroupUseCase {
    suspend operator fun invoke(groupId: String): Result<Unit>
}