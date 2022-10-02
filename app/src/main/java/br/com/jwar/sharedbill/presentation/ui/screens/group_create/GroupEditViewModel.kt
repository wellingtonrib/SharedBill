package br.com.jwar.sharedbill.presentation.ui.screens.group_create

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.usecases.CreateGroupUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupEditContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupEditContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupEditContract.Effect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Idle

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnSaveClick -> onSaveClick(event.name)
        }
    }

    private fun onSaveClick(name: String) = viewModelScope.launch {
        createGroupUseCase(name).collect { resource ->
            when(resource) {
                is Resource.Loading -> setState { State.Saving }
                is Resource.Success -> sendEffect { Effect.OpenGroupSaved(resource.data) }
                is Resource.Failure -> sendEffect { Effect.ShowError(resource.throwable.message.orEmpty()) }
            }
        }
    }
}