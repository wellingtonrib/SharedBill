package br.com.jwar.sharedbill.domain.usecases

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface SendPaymentUseCase {
    suspend operator fun invoke(payment: Payment, group: Group): Flow<Resource<Group>>
}