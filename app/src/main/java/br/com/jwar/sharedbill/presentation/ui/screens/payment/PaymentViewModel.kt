package br.com.jwar.sharedbill.presentation.ui.screens.payment

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.Result
import br.com.jwar.sharedbill.domain.usecases.CreatePaymentUseCase
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.domain.usecases.SendPaymentUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val createPaymentUseCase: CreatePaymentUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
    private val userToUserUiModelMapper: UserToUserUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State(isLoading = true)

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnInit -> onInit(event.groupId)
            is Event.OnCreatePayment -> onCreatePayment()
            is Event.OnPaymentParamsChange -> setPaymentParams(event.params)
        }
    }

    private fun onInit(groupId: String) = viewModelScope.launch {
        setLoadingState()
        when (val result = getGroupByIdUseCase(groupId, false)) {
            is Result.Success -> setPaymentParams(getPaymentParams(result.data))
            is Result.Error -> handlePaymentError(result.exception)
        }
    }

    private fun onCreatePayment() = viewModelScope.launch {
        setLoadingState()
        when (val result = createPaymentUseCase(getCurrentPaymentParams())) {
            is Result.Success -> onSendPayment(result.data)
            is Result.Error -> handlePaymentError(result.exception)
        }
    }

    private fun onSendPayment(payment: Payment) = viewModelScope.launch {
        setLoadingState()
        when (val result = sendPaymentUseCase(payment)) {
            is Result.Success -> sendFinishEffect()
            is Result.Error -> handlePaymentError(result.exception)
        }
    }

    private fun getPaymentParams(group: Group): SendPaymentParams =
        groupToGroupUiModelMapper.mapFrom(group).let { groupUiModel ->
            SendPaymentParams(
                group = groupUiModel,
                paidBy = userToUserUiModelMapper.mapFrom(group.findCurrentUser() ?: group.owner),
                paidTo = groupUiModel.members
            )
        }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun setPaymentParams(params: SendPaymentParams) =
        setState { it.copy(isLoading = false, params = params) }

    private fun getCurrentPaymentParams() = uiState.value.params ?: SendPaymentParams()

    private fun handlePaymentError(throwable: Throwable) {
        val paymentError = PaymentUiError.mapFrom(throwable)
        setState { it.copy(isLoading = false, params = it.params?.copy(error = paymentError)) }
        sendEffect { Effect.ShowError(paymentError.message) }
    }

    private fun sendFinishEffect() = sendEffect { Effect.Finish }
}