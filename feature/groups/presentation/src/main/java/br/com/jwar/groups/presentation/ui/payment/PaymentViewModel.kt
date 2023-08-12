package br.com.jwar.groups.presentation.ui.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.groups.presentation.navigation.GROUP_ID_ARG
import br.com.jwar.groups.presentation.navigation.PAYMENT_TYPE_ARG
import br.com.jwar.groups.presentation.ui.payment.PaymentContract.Effect
import br.com.jwar.groups.presentation.ui.payment.PaymentContract.Event
import br.com.jwar.groups.presentation.ui.payment.PaymentContract.State
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.usecases.CreatePaymentUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.SendPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val createPaymentUseCase: CreatePaymentUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
): BaseViewModel<Event, State, Effect>() {

    private val groupId: String = checkNotNull(savedStateHandle[GROUP_ID_ARG])
    private val paymentType = PaymentType.valueOf(checkNotNull(savedStateHandle[PAYMENT_TYPE_ARG]))

    private lateinit var group: GroupUiModel

    init { onInit(groupId, paymentType) }

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnSavePayment -> onSavePayment(event.payment)
        }
    }

    private fun onInit(groupId: String, paymentType: PaymentType) = viewModelScope.launch {
        setLoadingState()
        getGroupByIdUseCase(groupId, false)
            .onSuccess { group -> onGroupLoaded(group) }
            .onFailure { handlePaymentError(it) }
    }

    private fun onGroupLoaded(group: Group) =
        setState {
            this.group = groupToGroupUiModelMapper.mapFrom(group)
            State.Loaded(group = this.group)
        }

    private fun onSavePayment(payment: PaymentUiModel) = viewModelScope.launch {
        with(payment) {
            createPaymentUseCase(
                description = description,
                value = value,
                date = createdAt,
                paidById = paidBy.uid,
                paidToIds = paidTo.map { it.uid },
                groupId = groupId,
                paymentType = paymentType,
            ).onSuccess { onPaymentCreated(it) }
                .onFailure { handlePaymentError(it) }
        }
    }

    private fun onPaymentCreated(payment: Payment) = viewModelScope.launch {
        setLoadingState()
        sendPaymentUseCase(payment)
            .onSuccess { sendFinishEffect() }
            .onFailure { handlePaymentError(it) }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun handlePaymentError(throwable: Throwable) =
        setState {
            State.Loaded(group = this.group, error = PaymentUiError.mapFrom(throwable))
        }

    private fun sendFinishEffect() = sendEffect { Effect.Finish }
}