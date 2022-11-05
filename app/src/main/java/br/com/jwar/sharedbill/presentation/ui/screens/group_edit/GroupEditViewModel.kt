package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.domain.usecases.GroupAddMemberUseCase
import br.com.jwar.sharedbill.domain.usecases.GroupRemoveMemberUseCase
import br.com.jwar.sharedbill.domain.usecases.SaveGroupUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val groupAddMemberUseCase: GroupAddMemberUseCase,
    private val saveGroupUseCase: SaveGroupUseCase,
    private val groupRemoveMemberUseCase: GroupRemoveMemberUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnRequestEdit -> onRequestEdit(event.groupId)
            is Event.OnSaveGroupClick -> onSaveGroupClick()
            is Event.OnSaveMemberClick -> onSaveMemberClick(event.userName, event.groupId)
            is Event.OnMemberSelectionChange -> onMemberSelect(event.user)
            is Event.OnMemberDeleteClick -> onMemberDeleteClick(event.userId, event.groupId)
            is Event.OnGroupUpdated -> onGroupUpdated(event.group)
        }
    }

    private fun onRequestEdit(groupId: String, refresh: Boolean = true) = viewModelScope.launch {
        getGroupByIdUseCase(groupId, refresh).collect { resource ->
            when(resource) {
                is Resource.Loading -> setLoadingState()
                is Resource.Success -> setEditingState(resource.data)
                is Resource.Failure -> setErrorState(groupId, resource.throwable)
            }
        }
    }

    private fun onSaveGroupClick() = viewModelScope.launch {
        val group = getEditingGroup() ?: return@launch
        saveGroupUseCase(group.id, group.title).collect { resource ->
            when(resource) {
                is Resource.Loading -> setLoadingState()
                is Resource.Success -> setEditingState(resource.data)
                is Resource.Failure -> setErrorState(group.id, resource.throwable)
            }
        }
    }

    private fun onSaveMemberClick(userName: String, groupId: String) = viewModelScope.launch {
        groupAddMemberUseCase(userName, groupId).collect { resource ->
            when(resource) {
                is Resource.Loading -> setLoadingState()
                is Resource.Success -> setEditingState(resource.data, userName)
                is Resource.Failure -> setErrorState(groupId, resource.throwable)
            }
        }
    }

    private fun onMemberDeleteClick(userId: String, groupId: String) = viewModelScope.launch {
        groupRemoveMemberUseCase(userId, groupId).collect { resource ->
            when(resource) {
                is Resource.Loading -> setLoadingState()
                is Resource.Success -> setEditingState(resource.data)
                is Resource.Failure -> setErrorState(groupId, resource.throwable)
            }
        }
    }

    private fun setLoadingState() = setState { State.Loading }

    private fun setEditingState(group: Group, selectedMemberName: String? = null) =
        setState {
            with(groupToGroupUiModelMapper.mapFrom(group)) {
                State.Editing(
                    group = this,
                    selectedMember = this.members.firstOrNull { it.name == selectedMemberName }
                )
            }
        }

    private fun setEditingState(group: GroupUiModel) =
        setState {
            State.Editing(group = group)
        }

    private fun setErrorState(groupId: String, throwable: Throwable) {
        sendEffect {
            onRequestEdit(groupId, false)
            Effect.ShowError(throwable.message.orEmpty())
        }
    }

    private fun onGroupUpdated(group: GroupUiModel) = setEditingState(group)

    private fun onMemberSelect(user: UserUiModel?) {
        val group = getEditingGroup() ?: return
        setState {
            State.Editing(group = group, selectedMember = user)
        }
    }

    private fun getEditingGroup() = (uiState.value as? State.Editing)?.group
}