package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnRequestGroup -> onRequestGroup(event.groupId)
            is Event.OnManageClick -> onManageClick()
            is Event.OnNewPaymentClick -> onNewPaymentClick(event.groupId)
        }
    }

    private fun onRequestGroup(groupId: String) = viewModelScope.launch {
        getGroupByIdUseCase(groupId, true).collect { resource ->
            when(resource) {
                is Resource.Loading -> setLoadingState()
                is Resource.Success -> setLoadedState(resource)
                is Resource.Failure -> setErrorState(resource)
            }
        }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setErrorState(resource: Resource.Failure) =
        setState { State.Error(resource.throwable.message.orEmpty()) }

    private fun setLoadedState(resource: Resource.Success<Group>) =
        setState { State.Loaded(groupToGroupUiModelMapper.mapFrom(resource.data)) }

    private fun onNewPaymentClick(groupId: String) =
        sendEffect { Effect.OpenNewPayment(groupId) }

    private fun onManageClick() =
        sendEffect { Effect.OpenGroupMembers }
}