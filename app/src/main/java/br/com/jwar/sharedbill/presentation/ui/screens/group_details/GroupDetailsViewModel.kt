package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Result
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnInit -> onInit(event.groupId, event.refresh)
            is Event.OnManageClick -> onManageClick()
            is Event.OnNewPaymentClick -> onNewPaymentClick(event.groupId)
        }
    }

    private fun onInit(groupId: String, refresh: Boolean) = viewModelScope.launch {
        setLoadingState()
        when(val result = getGroupByIdUseCase(groupId, refresh)) {
            is Result.Success -> setLoadedState(result.data)
            is Result.Error -> setErrorState(result.exception)
        }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setErrorState(throwable: Exception) =
        setState { State.Error(throwable.message.orEmpty()) }

    private fun setLoadedState(group: Group) =
        setState { State.Loaded(groupToGroupUiModelMapper.mapFrom(group)) }

    private fun onNewPaymentClick(groupId: String) =
        sendEffect { Effect.OpenNewPayment(groupId) }

    private fun onManageClick() =
        sendEffect { Effect.OpenGroupMembers }
}