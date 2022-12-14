package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdStreamUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val getGroupByIdStreamUseCase: GetGroupByIdStreamUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnInit -> onInit(event.groupId)
            is Event.OnManage -> onManage()
            is Event.OnNewPayment -> onNewPayment(event.groupId)
        }
    }

    private fun onInit(groupId: String) = viewModelScope.launch {
        getGroupByIdStreamUseCase(groupId)
            .onStart { setLoadingState() }
            .collect { result ->
                result.onSuccess { setLoadedState(it) }.onFailure { setErrorState(it) }
            }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setErrorState(exception: Throwable) =
        setState { State.Error(exception.message.orEmpty()) }

    private fun setLoadedState(group: Group) {
        if (group.title.isBlank()) onManage()
        else setState { State.Loaded(groupToGroupUiModelMapper.mapFrom(group)) }
    }

    private fun onNewPayment(groupId: String) = sendEffect { Effect.OpenNewPayment(groupId) }

    private fun onManage() = sendEffect { Effect.OpenGroupEdit }
}