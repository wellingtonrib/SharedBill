package br.com.jwar.sharedbill.groups.presentation.ui.group_list

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.account.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.core.designsystem.util.UiText
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.usecases.CreateGroupUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.DeleteGroupUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupsStreamUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.JoinGroupUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.LeaveGroupUseCase
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiError
import br.com.jwar.sharedbill.groups.presentation.ui.group_list.GroupListContract.Effect
import br.com.jwar.sharedbill.groups.presentation.ui.group_list.GroupListContract.Event
import br.com.jwar.sharedbill.groups.presentation.ui.group_list.GroupListContract.State
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
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnInit -> onInit()
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
                    .onFailure { setErrorState(it, Event.OnInit) }
            }
    }

    private fun onGroupCreate(title: String) = viewModelScope.launch {
        setLoadingState()
        createGroupUseCase(title)
            .onSuccess { onGroupCreated(it) }
            .onFailure { setErrorState(it, Event.OnInit) }
    }

    private fun onGroupCreated(groupId: String) =
        sendEffect { Effect.NavigateToGroupEdit(groupId) }

    private fun onGroupJoin(code: String) = viewModelScope.launch {
        joinGroupUseCase(code)
            .onSuccess { onGroupSelect(it) }
            .onFailure { setErrorEffect(it) }
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
        setState {
            val updatedGroups = getCurrentLoadedGroups().filterNot { it.id == groupId }
            State.Loaded(updatedGroups)
        }

    private fun setErrorState(throwable: Throwable, event: Event) {
        if (throwable is UserNotFoundException) {
            sendEffect { Effect.NavigateToAuth }
        } else {
            setState {
                val error = GroupUiError.mapFrom(throwable)
                State.Error(error.message, UiText.StringResource(R.string.label_refresh_groups), event)
            }
        }
    }

    private fun setErrorEffect(throwable: Throwable) =
        sendEffect {
            val error = GroupUiError.mapFrom(throwable)
            Effect.ShowError(error.message)
        }

    private fun onGroupSelect(groupId: String) =
        sendEffect { Effect.NavigateToGroupDetails(groupId) }
}