package br.com.jwar.sharedbill.presentation.ui.screens.group_members

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_members.GroupMembersContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_members.GroupMembersContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_members.GroupMembersContract.Effect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupMembersViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnRequestMembers -> onRequestMembers(event.groupId, event.refresh)
            is Event.OnAddMemberClick -> onAddMemberClick()
            is Event.OnMemberSelect -> onMemberSelect()
            is Event.OnSaveMemberClick -> onSaveMemberClick()
        }
    }

    private fun onSaveMemberClick() {
        TODO("Not yet implemented")
    }

    private fun onMemberSelect() {
        sendEffect { Effect.OpenMemberDetails }
    }

    private fun onAddMemberClick() {
        sendEffect { Effect.OpenMemberCreate }
    }

    private fun onRequestMembers(groupId: String, refresh: Boolean) = viewModelScope.launch {
        getGroupByIdUseCase(groupId, refresh).collect { resource ->
            when(resource) {
                is Resource.Loading -> { setState { State.Loading }}
                is Resource.Success -> { setState { State.Loaded(resource.data.members) }}
                is Resource.Failure -> { setState { State.Error(resource.throwable.message.orEmpty()) }}
            }
        }
    }

}