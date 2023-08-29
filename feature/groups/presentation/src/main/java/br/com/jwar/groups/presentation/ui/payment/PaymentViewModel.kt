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
import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.usecases.CreatePaymentUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.SendPaymentUseCase
import br.com.jwar.sharedbill.groups.presentation.R
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
    private val stringProvider: StringProvider,
): BaseViewModel<Event, State, Effect>() {

    private val groupId: String = checkNotNull(savedStateHandle[GROUP_ID_ARG])
    private val paymentType = PaymentType.valueOf(checkNotNull(savedStateHandle[PAYMENT_TYPE_ARG]))

    init { onInit(groupId, paymentType) }

    override fun getInitialState(): State = State(isLoading = true)

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnSavePayment -> onSavePayment(event.payment)
        }
    }

    private fun onInit(groupId: String, paymentType: PaymentType) = viewModelScope.launch {
        getGroupByIdUseCase(groupId, false)
            .onSuccess { group -> onGroupLoaded(group, paymentType) }
            .onFailure { setErrorState(it) }
    }

    private fun onGroupLoaded(group: Group, paymentType: PaymentType) =
        setLoadedState(group, paymentType)

    private fun onSavePayment(payment: PaymentUiModel) = viewModelScope.launch {
        setPaymentState(payment)
        createPayment(payment)
    }

    private suspend fun createPayment(payment: PaymentUiModel) = with(payment) {
        createPaymentUseCase(
            description = description,
            value = value,
            date = createdAt,
            paidById = paidBy.uid,
            paidToIds = paidTo.map { it.uid },
            groupId = groupId,
            paymentType = paymentType,
        ).onSuccess { onPaymentCreated(it) }
            .onFailure { setErrorState(it) }
    }

    private fun onPaymentCreated(payment: Payment) = viewModelScope.launch {
        setLoadingState()
        sendPayment(payment)
    }

    private suspend fun sendPayment(payment: Payment) {
        sendPaymentUseCase(payment)
            .onSuccess { sendFinishEffect() }
            .onFailure { setErrorState(it) }
    }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun setPaymentState(payment: PaymentUiModel) =
        setState { it.copy(paymentUiModel = payment) }

    private fun setLoadedState(group: Group, paymentType: PaymentType) =
        setState { state ->
            val groupUiModel = groupToGroupUiModelMapper.mapFrom(group)
            state.copy(
                isLoading = false,
                title = getLoadedStateTitle(paymentType),
                inputFields = getLoadedStateFields(groupUiModel, paymentType),
                paymentUiModel = getLoadedStatePayment(groupUiModel, paymentType),
            )
        }

    private fun getLoadedStateTitle(paymentType: PaymentType) = stringProvider.getString(
        when (paymentType) {
            PaymentType.EXPENSE -> R.string.label_payment_new_expense
            PaymentType.SETTLEMENT -> R.string.label_payment_new_settlement
        }
    )

    private fun getLoadedStateFields(
        groupUiModel: GroupUiModel,
        paymentType: PaymentType
    ) = (paymentType == PaymentType.EXPENSE).let { isExpense ->
        listOfNotNull<PaymentContract.Field>(
            PaymentContract.Field.DescriptionField(requestFocus = isExpense).takeIf { isExpense },
            PaymentContract.Field.ValueField(requestFocus = isExpense.not()),
            PaymentContract.Field.DateField().takeIf { isExpense },
            PaymentContract.Field.PaidByField(options = groupUiModel.membersToSelect),
            PaymentContract.Field.PaidToField(options = groupUiModel.members, isMultiSelect = isExpense)
        )
    }

    private fun getLoadedStatePayment(
        groupUiModel: GroupUiModel,
        paymentType: PaymentType
    ) = when (paymentType) {
        PaymentType.EXPENSE -> PaymentUiModel(
            paidBy = groupUiModel.members.first(),
            paidTo = groupUiModel.members,
            paymentType = paymentType,
        )
        PaymentType.SETTLEMENT -> PaymentUiModel(
            description = stringProvider.getString(R.string.label_settlement),
            paidBy = groupUiModel.members.first(),
            paidTo = groupUiModel.members.take(1),
            paymentType = paymentType,
        )
    }

    private fun setErrorState(throwable: Throwable) =
        setState { state ->
            val error = PaymentUiError.mapFrom(throwable)
            val inputFields = state.inputFields.mapError(error)
            val genericError = error.takeIf { inputFields.none { it.hasError } }
            state.copy(
                isLoading = false,
                inputFields = inputFields,
                genericError = genericError
            )
        }

    private fun List<PaymentContract.Field>.mapError(error: PaymentUiError) = this.map { field ->
        when(field) {
            is PaymentContract.Field.DescriptionField ->
                field.copy(error = error as? PaymentUiError.InvalidDescriptionError)
            is PaymentContract.Field.ValueField ->
                field.copy(error = error as? PaymentUiError.InvalidValueError)
            is PaymentContract.Field.DateField ->
                field.copy(error = error as? PaymentUiError.InvalidDateError)
            is PaymentContract.Field.PaidByField ->
                field.copy(error = error as? PaymentUiError.InvalidPaidByError)
            is PaymentContract.Field.PaidToField ->
                field.copy(error = error as? PaymentUiError.InvalidPaidToError)
            else -> field
        }
    }

    private fun sendFinishEffect() = sendEffect { Effect.Finish }
}