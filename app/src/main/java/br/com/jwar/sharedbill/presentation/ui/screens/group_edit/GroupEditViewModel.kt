package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.domain.usecases.GroupAddMemberUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val groupAddMemberUseCase: GroupAddMemberUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnRequestEdit -> onRequestEdit(event.groupId)
            is Event.OnSaveGroupClick -> onSaveGroupClick(event.group)
            is Event.OnSaveMemberClick -> onSaveMemberClick(event.userName, event.groupId)
            is Event.OnMemberSelectionChange -> onMemberSelect(event.user)
        }
    }

    private fun onMemberSelect(user: User?) {
        setState { currentState ->
            val editing = (currentState as? State.Editing) ?: return@setState currentState
            State.Editing(editing.group, selectedMember = user)
        }
    }

    private fun onRequestEdit(groupId: String) = viewModelScope.launch {
        getGroupByIdUseCase(groupId, true).collect { resource ->
            when(resource) {
                is Resource.Loading -> setState { State.Loading }
                is Resource.Success -> setState { State.Editing(resource.data) }
                is Resource.Failure -> sendEffect { Effect.ShowError(resource.throwable.message.orEmpty()) }
            }
        }
    }

    private fun onSaveGroupClick(group: Group) = viewModelScope.launch {
        TODO("Not yet implemented")
    }

    private fun onSaveMemberClick(userName: String, groupId: String) = viewModelScope.launch {
        groupAddMemberUseCase(userName, groupId).collect { resource ->
            when(resource) {
                is Resource.Loading -> setState { State.Loading }
                is Resource.Success -> setState {
                    State.Editing(
                        group = resource.data,
                        selectedMember = resource.data.members.firstOrNull { it.name == userName }
                    )
                }
                is Resource.Failure -> sendEffect { Effect.ShowError(resource.throwable.message.orEmpty()) }
            }
        }
    }
}