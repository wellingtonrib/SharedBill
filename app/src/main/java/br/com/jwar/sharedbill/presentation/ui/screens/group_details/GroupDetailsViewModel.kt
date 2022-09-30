package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.presentation.core.BaseViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.*
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.*
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
            is Event.OnLoadGroup -> onLoadGroup(event.groupId)
        }
    }

    private fun onLoadGroup(group: String) = viewModelScope.launch {
        getGroupByIdUseCase(group).collect { resource ->
            when(resource) {
                is Resource.Success -> setState { Loaded(resource.data) }
                is Resource.Failure -> setState { Error(resource.throwable.message.orEmpty()) }
                else -> setState { Loading }
            }
        }
    }
}