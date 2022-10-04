package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Event.OnRequestGroup
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Event.OnManageClick
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Loaded
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Loading
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Error
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Effect.OpenGroupMembers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is OnRequestGroup -> onRequestGroup(event.groupId)
            is OnManageClick -> onManageClick()
        }
    }

    private fun onManageClick() {
        sendEffect { OpenGroupMembers }
    }

    private fun onRequestGroup(group: String) = viewModelScope.launch {
        getGroupByIdUseCase(group).collect { resource ->
            when(resource) {
                is Resource.Loading -> setState { Loading }
                is Resource.Success -> setState { Loaded(resource.data) }
                is Resource.Failure -> setState { Error(resource.throwable.message.orEmpty()) }
            }
        }
    }
}