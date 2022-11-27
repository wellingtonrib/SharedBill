package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.usecases.*
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    private val getGroupByIdStreamUseCase: GetGroupByIdStreamUseCase,
    private val groupAddMemberUseCase: GroupAddMemberUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val groupRemoveMemberUseCase: GroupRemoveMemberUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State(isLoading = true)

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnInit -> onInit(event.groupId)
            is Event.OnSaveGroupClick -> onSaveGroupClick()
            is Event.OnSaveMemberClick -> onSaveMemberClick(event.userName, event.groupId)
            is Event.OnMemberSelectionChange -> onMemberSelect(event.user)
            is Event.OnMemberDeleteClick -> onMemberDeleteClick(event.userId, event.groupId)
            is Event.OnGroupUpdated -> onGroupUpdated(event.group)
        }
    }

    private fun onInit(groupId: String) = viewModelScope.launch {
        getGroupByIdStreamUseCase(groupId)
            .onStart { setLoadingState() }
            .collect { result ->
                result.onSuccess { setEditingGroup(it) }
                    .onFailure { sendErrorEffect(it) }
            }
    }

    private fun onSaveGroupClick() = viewModelScope.launch {
        setLoadingState()
        val group = getEditingGroup()
        updateGroupUseCase(group.id, group.title)
            .onSuccess { setEditingGroup(it) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onSaveMemberClick(userName: String, groupId: String) = viewModelScope.launch {
        setLoadingState()
        groupAddMemberUseCase(userName, groupId)
            .onSuccess { setEditingGroup(it, userName) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onMemberDeleteClick(userId: String, groupId: String) = viewModelScope.launch {
        setLoadingState()
        groupRemoveMemberUseCase(userId, groupId)
            .onSuccess { setEditingGroup(it) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun getEditingGroup() = uiState.value.group ?: GroupUiModel()

    private fun setEditingGroup(group: Group, selectedMemberName: String? = null) =
        setState {
            with(groupToGroupUiModelMapper.mapFrom(group)) {
                it.copy(
                    isLoading = false,
                    group = this,
                    selectedMember = this.members.firstOrNull { it.name == selectedMemberName }
                )
            }
        }

    private fun setEditingGroup(group: GroupUiModel) = setState { it.copy(group = group) }

    private fun sendErrorEffect(throwable: Throwable) =
        sendEffect { Effect.ShowError(throwable.message.orEmpty()) }

    private fun onGroupUpdated(group: GroupUiModel) = setEditingGroup(group)

    private fun onMemberSelect(user: UserUiModel?) = setState { it.copy(selectedMember = user) }
}