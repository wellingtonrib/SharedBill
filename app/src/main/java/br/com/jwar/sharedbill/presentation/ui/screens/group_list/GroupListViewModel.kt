package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.exceptions.UserException.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.usecases.CreateGroupUseCase
import br.com.jwar.sharedbill.domain.usecases.GetGroupsStreamUseCase
import br.com.jwar.sharedbill.domain.usecases.GroupJoinUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(
    private val getGroupsStreamUseCase: GetGroupsStreamUseCase,
    private val createGroupUseCase: CreateGroupUseCase,
    private val groupJoinUseCase: GroupJoinUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnInit -> onInit()
            is Event.OnGroupCreate -> onGroupCreate(event.title)
            is Event.OnGroupSelect -> onGroupSelect(event.groupId)
            is Event.OnGroupJoin -> onGroupJoin(event.inviteCode)
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
            .onSuccess { onGroupSelect(it.id) }
            .onFailure { setErrorState(it) }
    }

    private fun onGroupJoin(code: String) = viewModelScope.launch {
        setLoadingState()
        groupJoinUseCase(code)
            .onSuccess { onGroupSelect(it.id) }
            .onFailure { setErrorState(it) }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setLoadedState(groups: List<Group>) =
        setState {
            if (groups.isEmpty()) State.Empty
            else State.Loaded(groups.map { groupToGroupUiModelMapper.mapFrom(it) })
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