package br.com.jwar.sharedbill.domain.usecases

interface CreateGroupUseCase {
    suspend operator fun invoke(title: String): Result<String>
}