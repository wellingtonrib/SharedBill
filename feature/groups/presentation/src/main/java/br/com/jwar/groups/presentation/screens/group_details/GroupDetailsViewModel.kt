package br.com.jwar.groups.presentation.screens.group_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.groups.presentation.models.PaymentType
import br.com.jwar.groups.presentation.navigation.GROUP_ID_ARG
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsContract.Effect
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsContract.Event
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsContract.State
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGroupByIdStreamUseCase: GetGroupByIdStreamUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    private val groupId: String = checkNotNull(savedStateHandle[GROUP_ID_ARG])

    init { onInit() }

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnRefreshGroup -> onInit()
            is Event.OnEditGroup -> onEditGroup()
            is Event.OnNewPayment -> onNewPayment(event.paymentType)
            is Event.OnShareBalance -> onShareBalance(event.balance)
        }
    }

    private fun onInit() = viewModelScope.launch {
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