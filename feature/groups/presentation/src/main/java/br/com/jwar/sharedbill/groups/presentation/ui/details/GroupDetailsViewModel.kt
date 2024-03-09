package br.com.jwar.sharedbill.groups.presentation.ui.details

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.usecases.DeletePaymentUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdStreamUseCase
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.ui.details.GroupDetailsContract.Effect
import br.com.jwar.sharedbill.groups.presentation.ui.details.GroupDetailsContract.Event
import br.com.jwar.sharedbill.groups.presentation.ui.details.GroupDetailsContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val getGroupByIdStreamUseCase: GetGroupByIdStreamUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
    private val deletePaymentUseCase: DeletePaymentUseCase,
    private val stringProvider: StringProvider
) : BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnInit -> onInit(event.groupId)
            is Event.OnEditGroup -> onEditGroup()
            is Event.OnNewPayment -> onNewPayment(event.paymentType)
            is Event.OnShareBalance -> onShareBalance(event.balance)
            is Event.OnDeletePayment -> onDeletePayment(event.paymentId, event.groupId)
        }
    }

    private fun onInit(groupId: String) = viewModelScope.launch {
        getGroupByIdStreamUseCase(groupId)
            .onStart { setLoadingState() }
            .collect { result ->
                result
                    .onSuccess { setLoadedState(it) }
                    .onFailure { setErrorState(it) }
            }
    }

    private fun onNewPayment(paymentType: PaymentType) =
        sendEffect { Effect.NavigateToNewPayment(paymentType) }

    private fun onDeletePayment(paymentId: String, groupId: String) = viewModelScope.launch {
        deletePaymentUseCase(
            paymentId = paymentId,
            groupId = groupId,
            refundSuffix = stringProvider.getString(R.string.label_payment_refund)
        )
    }

    private fun onShareBalance(balance: String) {
        sendEffect { Effect.ShareBalance(balance) }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setErrorState(exception: Throwable) =
        setState { State.Error(exception.message.orEmpty()) }

    private fun setLoadedState(group: Group) =
        setState { State.Loaded(groupToGroupUiModelMapper.mapFrom(group)) }

    private fun onEditGroup() = sendEffect { Effect.NavigateToGroupEdit }
}
