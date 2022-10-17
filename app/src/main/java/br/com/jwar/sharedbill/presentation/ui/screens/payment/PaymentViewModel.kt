package br.com.jwar.sharedbill.presentation.ui.screens.payment

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdWithCurrentMemberUseCase
import br.com.jwar.sharedbill.domain.usecases.SendPaymentUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.SendPaymentParams
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val getGroupByIdWithCurrentMemberUseCase: GetGroupByIdWithCurrentMemberUseCase,
    private val groupUiModelMapper: GroupToGroupUiModelMapper,
    private val userToUserUiModelMapper: UserToUserUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnRequestGroup -> onRequestGroup(event.groupId)
            is Event.SendPayment -> onSendPayment(event.params)
        }
    }

    private fun onSendPayment(params: SendPaymentParams) = viewModelScope.launch {
        //TODO: Handle fields validations
        sendPaymentUseCase(params).collect { resource ->
            when(resource) {
                is Resource.Loading -> setLoadingState()
                is Resource.Success -> sendFinishEffect()
                is Resource.Failure -> handlePaymentError(params, resource.throwable)
            }
        }
    }

    private fun onRequestGroup(groupId: String) = viewModelScope.launch {
        getGroupByIdWithCurrentMemberUseCase(groupId, true).collect { resource ->
            when(resource) {
                is Resource.Loading -> setLoadingState()
                is Resource.Success -> setEditingState(resource.data.first, resource.data.second)
                is Resource.Failure -> setErrorState(resource.throwable)
            }
        }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setEditingState(group: Group, user: User) =
        setState {
            val groupUiModel = groupUiModelMapper.mapFrom(group)
            val userUiModel = userToUserUiModelMapper.mapFrom(user)
            val membersUiModels = groupUiModel.members
            State.Editing(
                SendPaymentParams(group = groupUiModel, paidBy = userUiModel, paidTo = membersUiModels)
            )
        }

    private fun setEditingState(params: SendPaymentParams) = setState { State.Editing(params) }

    private fun setErrorState(throwable: Throwable) =
        setState { State.Error(throwable.message.orEmpty()) }

    private fun sendErrorEffect(throwable: Throwable) =
        sendEffect { Effect.ShowError(throwable.message.orEmpty()) }

    private fun handlePaymentError(params: SendPaymentParams, throwable: Throwable) {
        setEditingState(params)
        sendErrorEffect(throwable)
    }

    private fun sendFinishEffect() = sendEffect { Effect.Finish }
}