package br.com.jwar.sharedbill.groups.domain.usecases

interface CreateGroupUseCase {
    suspend operator fun invoke(title: String): Result<String>
}