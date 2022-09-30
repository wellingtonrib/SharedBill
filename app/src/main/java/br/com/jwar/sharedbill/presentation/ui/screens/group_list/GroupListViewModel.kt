package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource.Loading
import br.com.jwar.sharedbill.domain.model.Resource.Failure
import br.com.jwar.sharedbill.domain.model.Resource.Success
import br.com.jwar.sharedbill.domain.usecases.GetAllGroupsUseCase
import br.com.jwar.sharedbill.presentation.core.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect.OpenGroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.Effect.OpenGroupCreate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnGetGroups -> onGetAllGroups(event.refresh)
            is Event.OnNewGroup -> onNewGroup()
            is Event.OnGroupSelected -> onOpenGroupDetails(event.group)
        }
    }

    private fun onGetAllGroups(refresh: Boolean) = viewModelScope.launch {
        getAllGroupsUseCase(refresh).collect { resource ->
            when(resource) {
                is Loading -> setState { State.Loading }
                is Success -> setState { State.Loaded(resource.data) }
                is Failure -> setState { State.Error(resource.throwable.message) }
            }
        }
    }

    private fun onNewGroup() = viewModelScope.launch {
        sendEffect { OpenGroupCreate }
    }

    private fun onOpenGroupDetails(group: Group) {
        sendEffect { OpenGroupDetails(group.id) }
    }
}