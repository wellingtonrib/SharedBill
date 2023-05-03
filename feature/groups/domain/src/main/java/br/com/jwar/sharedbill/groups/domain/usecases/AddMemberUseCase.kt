package br.com.jwar.sharedbill.groups.domain.usecases

interface AddMemberUseCase {
    suspend operator fun invoke(userName: String, groupId: String): Result<String>
}