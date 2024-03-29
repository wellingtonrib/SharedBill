package br.com.jwar.sharedbill.groups.presentation.ui.payment

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.usecases.CreatePaymentUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.SendPaymentUseCase
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.Effect
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.Event
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.Field.DateField
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.Field.DescriptionField
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.Field.PaidByField
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.Field.PaidToField
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.Field.ValueField
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.State
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

private const val CURRENCY_TO_DOUBLE_DIVISOR = 100

@Stable
@HiltViewModel
class GroupPaymentViewModel @Inject constructor(
    override val stringProvider: StringProvider,
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val createPaymentUseCase: CreatePaymentUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
) : BaseViewModel<Event, State, Effect>(), GroupPaymentTrait {

    override fun getInitialState(): State = State(isLoading = true)

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnInit -> onInit(event.groupId, event.paymentType)
            is Event.OnDescriptionChange -> onDescriptionChange(event.description)
            is Event.OnValueChange -> onValueChange(event.value)
            is Event.OnDateChange -> onDateChange(event.dateTime)
            is Event.OnPaidByChange -> onPaidByChange(event.paidBy)
            is Event.OnPaidToChange -> onPaidToChange(event.paidTo)
            is Event.OnSavePayment -> onSavePayment(event.groupId, event.paymentType)
        }
    }

    private fun onInit(groupId: String, paymentType: PaymentType) = viewModelScope.launch {
        getGroupByIdUseCase(groupId, false)
            .onSuccess { group -> setLoadedState(group, paymentType) }
            .onFailure { setErrorState(it) }
    }

    private fun onDescriptionChange(description: String) = setState { state ->
        state.updateField<DescriptionField> { field -> field.copy(value = description, error = null) }
    }

    private fun onValueChange(value: String) = setState { state ->
        val currencyValue = value.fromCurrencyToDouble()
        val updatedState = state.updateFields { field ->
            when (field) {
                is ValueField -> field.copy(value = currencyValue, error = null)
                is PaidToField -> field.copy(sharedValue = calculateSharedValue(currencyValue, field.value))
                else -> field
            }
        }
        updatedState
    }

    private fun String.fromCurrencyToDouble() = this.toDoubleOrNull()
        ?.div(CURRENCY_TO_DOUBLE_DIVISOR).toString()

    private fun onDateChange(dateTime: Long) = setState { state ->
        state.updateField<DateField> { field -> field.copy(value = dateTime, error = null) }
    }

    private fun onPaidByChange(paidBy: GroupMemberUiModel) = setState { state ->
        val updatedState = state.updateFields { field ->
            when {
                field is PaidByField -> field.copy(value = paidBy, error = null)
                field is PaidToField && state.paymentType == PaymentType.SETTLEMENT -> {
                    val updatedOptions = state.groupUiModel.members.filterNot { it.uid == paidBy.uid }
                    val updatedValue = if (field.value.keys.contains(paidBy)) {
                        updatedOptions.take(1).associateWith { 1 }
                    } else {
                        field.value
                    }
                    field.copy(
                        value = ImmutableMap.copyOf(updatedValue),
                        options = ImmutableSet.copyOf(updatedOptions)
                    )
                }
                else -> field
            }
        }
        updatedState
    }

    private fun onPaidToChange(paidTo: ImmutableMap<GroupMemberUiModel, Int>) = setState { state ->
        state.updateField<PaidToField> { field ->
            field.copy(
                value = ImmutableMap.copyOf(paidTo),
                error = null,
                sharedValue = calculateSharedValue(
                    value = state.getFieldValue<ValueField, String>().orEmpty(),
                    paidTo = paidTo
                )
            )
        }
    }

    private fun onSavePayment(groupId: String, paymentType: PaymentType) = viewModelScope.launch {
        uiState.value.let { state ->
            val description = state.getFieldValue<DescriptionField, String>()
            val value = state.getFieldValue<ValueField, String>()
            val dateTime = state.getFieldValue<DateField, Long>()
            val paidById = state.getFieldValue<PaidByField, GroupMemberUiModel>()
            val paidToIds = state.getFieldValue<PaidToField, Map<GroupMemberUiModel, Int>>()
            createPaymentUseCase(
                description = description.orEmpty(),
                value = value.orEmpty(),
                dateTime = dateTime ?: Calendar.getInstance().timeInMillis,
                paidById = paidById?.uid.orEmpty(),
                paidToIds = paidToIds?.entries?.associate { it.key.uid to it.value }.orEmpty(),
                groupId = groupId,
                paymentType = paymentType,
            ).onSuccess { sendPayment(it) }
                .onFailure { setErrorState(it) }
        }
    }

    private suspend fun sendPayment(payment: Payment) {
        setLoadingState()
        sendPaymentUseCase(payment)
            .onSuccess { sendFinishEffect() }
            .onFailure { setErrorState(it) }
    }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun setLoadedState(group: Group, paymentType: PaymentType) =
        groupToGroupUiModelMapper.mapFrom(group).let { groupUiModel ->
            setState { state ->
                state.copy(
                    isLoading = false,
                    groupUiModel = groupUiModel,
                    paymentType = paymentType,
                    inputFields = getInputFields(groupUiModel, paymentType)
                )
            }
        }

    private fun setErrorState(throwable: Throwable) =
        setState { state ->
            val error = PaymentUiError.mapFrom(throwable)
            val inputFields = mapErrorHandler(state.inputFields, error)
            val genericError = error.takeIf { inputFields.none { it.hasError } }
            state.copy(
                isLoading = false,
                inputFields = ImmutableSet.copyOf(inputFields),
                genericError = genericError
            )
        }

    private fun sendFinishEffect() = sendEffect { Effect.Finish }
}
