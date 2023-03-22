package br.com.jwar.sharedbill.presentation.screens.group_list

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.account.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.usecases.*
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.models.GroupUiError
import br.com.jwar.sharedbill.presentation.screens.group_list.GroupListContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(
    private val getGroupsStreamUseCase: GetGroupsStreamUseCase,
    private val createGroupUseCase: CreateGroupUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val leaveGroupUseCase: LeaveGroupUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper
): br.com.jwar.sharedbill.core.common.BaseViewModel<Event, State, Effect>() {

    init { onInit() }

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnTryAgain -> onInit()
            is Event.OnGroupCreate -> onGroupCreate(event.title)
            is Event.OnGroupSelect -> onGroupSelect(event.groupId)
            is Event.OnGroupJoin -> onGroupJoin(event.inviteCode)
            is Event.OnGroupDelete -> onGroupDelete(event.groupId)
            is Event.OnGroupLeave -> onGroupLeave(event.groupId)
        }
    }

    private fun onInit() = viewModelScope.launch {
        getGroupsStreamUseCase()
            .onStart { setLoadingState() }
            .collect { result ->
                result.onSuccess { setLoadedState(it) }
                    .onFailure { setErrorState(it) }
            }
    }

    private fun onGroupCreate(title: String) = viewModelScope.launch {
        setLoadingState()
        createGroupUseCase(title)
            .onSuccess { onGroupCreated(it) }
            .onFailure { setErrorState(it) }
    }

    private fun onGroupCreated(groupId: String) =
        sendEffect { Effect.OpenGroupDetails(groupId) }

    private fun onGroupJoin(code: String) = viewModelScope.launch {
        setLoadingState()
        joinGroupUseCase(code)
            .onSuccess { onGroupSelect(it) }
            .onFailure { setErrorState(it) }
    }

    private fun onGroupDelete(groupId: String) = viewModelScope.launch {
        deleteGroupUseCase(groupId)
            .onSuccess { setLoadedStateRemoving(groupId) }
            .onFailure { setErrorEffect(it) }
    }

    private fun onGroupLeave(groupId: String) = viewModelScope.launch {
        leaveGroupUseCase(groupId)
            .onSuccess { setLoadedStateRemoving(groupId) }
            .onFailure { setErrorEffect(it) }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun getCurrentLoadedGroups() = (uiState.value as? State.Loaded)?.uiModel.orEmpty()

    private fun setLoadedState(groups: List<Group>) =
        setState { State.Loaded(groups.map { groupToGroupUiModelMapper.mapFrom(it) }) }

    private fun setLoadedStateRemoving(groupId: String) =
        setState { State.Loaded(getCurrentLoadedGroups().toMutableList().apply {
            removeIf { it.id == groupId }
        }.toList()) }

    private fun setErrorState(throwable: Throwable) {
        setState { State.Error(throwable.message) }
        if (throwable is UserNotFoundException) {
            sendEffect { Effect.GoToAuth }
        }
    }

    private fun setErrorEffect(throwable: Throwable) {
        val error = GroupUiError.mapFrom(throwable)
        sendEffect { Effect.Error(error.message) }
    }

    private fun onGroupSelect(groupId: String) = sendEffect { Effect.OpenGroupDetails(groupId) }
}