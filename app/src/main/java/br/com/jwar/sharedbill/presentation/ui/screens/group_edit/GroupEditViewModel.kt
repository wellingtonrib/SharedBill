package br.com.jwar.sharedbill.presentation.ui.screens.group_edit

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdStreamUseCase
import br.com.jwar.sharedbill.domain.usecases.GroupAddMemberUseCase
import br.com.jwar.sharedbill.domain.usecases.GroupRemoveMemberUseCase
import br.com.jwar.sharedbill.domain.usecases.UpdateGroupUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.models.GroupUiError
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        val groupEdited = getEditingGroup()
        setLoadingState()
        updateGroupUseCase(groupEdited.id, groupEdited.title)
            .onSuccess { sendSuccessEffect() }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onSaveMemberClick(userName: String, groupId: String) = viewModelScope.launch {
        setLoadingState()
        groupAddMemberUseCase(userName, groupId)
            .onSuccess { setShouldSelectMemberState(userName) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onMemberDeleteClick(userId: String, groupId: String) = viewModelScope.launch {
        setLoadingState()
        groupRemoveMemberUseCase(userId, groupId)
            .onSuccess { sendSuccessEffect() }
            .onFailure { sendErrorEffect(it) }
    }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun getEditingGroup() = uiState.value.group ?: GroupUiModel()

    private fun setEditingGroup(group: Group) =
        setState {
            val groupUiModel = groupToGroupUiModelMapper.mapFrom(group)
            val shouldSelectMemberName = it.shouldSelectMemberByName
            val selectedMember = groupUiModel.members.firstOrNull { member ->
                member.name == shouldSelectMemberName
            }
            it.copy(
                isLoading = false,
                group = groupUiModel,
                selectedMember = selectedMember,
                shouldSelectMemberByName = null
            )
        }

    private fun setEditingGroup(group: GroupUiModel) =
        setState { it.copy(isLoading = false, group = group) }

    private fun sendSuccessEffect() {
        setState { it.copy(isLoading = false) }
        sendEffect { Effect.ShowSuccess }
    }

    private fun sendErrorEffect(throwable: Throwable) {
        val error = GroupUiError.mapFrom(throwable)
        setState { it.copy(isLoading = false) }
        sendEffect { Effect.ShowError(error.message) }
    }

    private fun setShouldSelectMemberState(userName: String) =
        setState { it.copy(shouldSelectMemberByName = userName) }

    private fun onGroupUpdated(group: GroupUiModel) = setEditingGroup(group)

    private fun onMemberSelect(user: UserUiModel?) = setState { it.copy(selectedMember = user) }
}