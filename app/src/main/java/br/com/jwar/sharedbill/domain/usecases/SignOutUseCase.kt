package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface SignOutUseCase {
    suspend operator fun invoke(): Flow<Resource<Boolean>>
}