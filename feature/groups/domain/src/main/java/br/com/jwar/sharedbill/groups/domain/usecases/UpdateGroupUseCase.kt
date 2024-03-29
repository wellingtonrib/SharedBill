package br.com.jwar.sharedbill.groups.domain.usecases

interface UpdateGroupUseCase {
    suspend operator fun invoke(groupId: String, title: String): Result<Unit>
}
