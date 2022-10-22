package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource.Failure
import br.com.jwar.sharedbill.domain.model.Resource.Loading
import br.com.jwar.sharedbill.domain.model.Resource.Success
import br.com.jwar.sharedbill.domain.usecases.CreateGroupUseCase
import br.com.jwar.sharedbill.domain.usecases.GetAllGroupsUseCase
import br.com.jwar.sharedbill.domain.usecases.GroupJoinUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class GroupListViewModel @Inject constructor(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val createGroupUseCase: CreateGroupUseCase,
    private val groupJoinUseCase: GroupJoinUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnRequestGroups -> onRequestGroups(event.refresh)
            is Event.OnGroupCreate -> onGroupCreate(event.title)
            is Event.OnGroupSelect -> onGroupSelect(event.groupId)
            is Event.OnGroupJoin -> onGroupJoin(event.code)
        }
    }

    private fun onRequestGroups(refresh: Boolean) = viewModelScope.launch {
        getAllGroupsUseCase(refresh).collect { resource ->
            when(resource) {
                is Loading -> setLoadingState()
                is Success -> setLoadedState(resource)
                is Failure -> setErrorState(resource.throwable)
            }
        }
    }

    private fun onGroupCreate(title: String) = viewModelScope.launch {
        createGroupUseCase(title).collect { resource ->
            when(resource) {
                is Loading -> setLoadingState()
                is Success -> onGroupSelect(resource.data.id)
                is Failure -> setErrorState(resource.throwable)
            }
        }
    }

    private fun onGroupJoin(code: String) = viewModelScope.launch {
        groupJoinUseCase(code).collect { resource ->
            when(resource) {
                is Loading -> setLoadingState()
                is Success -> onGroupSelect(resource.data.id)
                is Failure -> setErrorState(resource.throwable)
            }
        }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setLoadedState(resource: Success<List<Group>>) {
        setState {
            if (resource.data.isEmpty())
                State.Empty
            else
                State.Loaded(resource.data.map { groupToGroupUiModelMapper.mapFrom(it) })
        }
    }

    private fun setErrorState(throwable: Throwable) {
        setState { State.Error(throwable.message) }
        if (throwable is UserNotFoundException) {
            sendEffect { Effect.GoToAuth }
        }
    }

    private fun onGroupSelect(groupId: String) =
        sendEffect { Effect.OpenGroupDetails(groupId) }
}