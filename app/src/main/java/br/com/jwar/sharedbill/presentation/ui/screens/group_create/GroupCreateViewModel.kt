package br.com.jwar.sharedbill.presentation.ui.screens.group_create

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.usecases.CreateGroupUseCase
import br.com.jwar.sharedbill.presentation.core.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupCreateContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupCreateContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupCreateContract.Effect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupCreateViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Idle

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnCreateGroup -> onGroupCreate(event.name)
        }
    }

    private fun onGroupCreate(name: String) = viewModelScope.launch {
        createGroupUseCase(name).collect { resource ->
            when(resource) {
                is Resource.Loading -> setState { State.Creating }
                is Resource.Success -> sendEffect { Effect.OpenGroupCreated(resource.data) }
                is Resource.Failure -> sendEffect { Effect.ShowError(resource.throwable.message.orEmpty()) }
            }
        }
    }
}