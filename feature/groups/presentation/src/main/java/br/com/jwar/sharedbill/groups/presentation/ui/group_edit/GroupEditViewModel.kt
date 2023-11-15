package br.com.jwar.sharedbill.groups.presentation.ui.group_edit

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.usecases.AddMemberUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.RemoveMemberUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.UpdateGroupUseCase
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiError
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.presentation.ui.group_edit.GroupEditContract.Effect
import br.com.jwar.sharedbill.groups.presentation.ui.group_edit.GroupEditContract.Event
import br.com.jwar.sharedbill.groups.presentation.ui.group_edit.GroupEditContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val addMemberUseCase: AddMemberUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val removeMemberUseCase: RemoveMemberUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State(isLoading = true)

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnInit -> onInit(event.groupId, event.selectedMemberId)
            is Event.OnSaveGroup -> onSaveGroup(event.groupId)
            is Event.OnSaveMember -> onSaveMember(event.groupId, event.userName)
            is Event.OnMemberSelectionChange -> onMemberSelectionChange(event.user)
            is Event.OnMemberDelete -> onDeleteMember(event.groupId, event.userId)
            is Event.OnGroupUpdated -> onGroupUpdated(event.group)
            is Event.OnShareInviteCode -> onShareInviteCode(event.inviteCode)
        }
    }

    private fun onInit(
        groupId: String,
        selectedMemberId: String? = null
    ) = viewModelScope.launch {
        getGroupByIdUseCase(groupId)
            .onSuccess { setEditingGroup(it); setSelectedMember(selectedMemberId) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onSaveGroup(groupId: String, finish: Boolean = true) = viewModelScope.launch {
        setLoadingState()
        updateGroupUseCase(groupId, getEditingGroup().title)
            .onSuccess { if (finish) sendNavigateToGroupDetailsEffect() }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onSaveMember(groupId: String, userName: String) = viewModelScope.launch {
        onSaveGroup(groupId, false)
        addMemberUseCase(userName, groupId)
            .onSuccess { selectedMemberId -> onInit(groupId, selectedMemberId) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onDeleteMember(groupId: String, userId: String) = viewModelScope.launch {
        setLoadingState()
        removeMemberUseCase(userId, groupId)
            .onSuccess { onInit(groupId) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onShareInviteCode(inviteCode: String) {
        sendEffect { Effect.ShareInviteCode(inviteCode) }
    }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun getEditingGroup() = uiState.value.uiModel ?: GroupUiModel()

    private fun setEditingGroup(group: Group) =
        setState { state ->
            state.copy(
                isLoading = false,
                uiModel = groupToGroupUiModelMapper.mapFrom(group),
            )
        }

    private fun setSelectedMember(selectedMemberId: String?) =
        setState { state ->
            val groupUiModel = getEditingGroup()
            val selectedMember = selectedMemberId?.let {
                groupUiModel.members.firstOrNull { it.uid == selectedMemberId }
            }
            state.copy(selectedMember = selectedMember)
        }

    private fun setEditingGroup(group: GroupUiModel) =
        setState { it.copy(isLoading = false, uiModel = group) }

    private fun sendNavigateToGroupDetailsEffect() {
        setState { it.copy(isLoading = false) }
        sendEffect { Effect.NavigateToGroupDetails }
    }

    private fun sendErrorEffect(throwable: Throwable) {
        setState { it.copy(isLoading = false) }
        sendEffect {
            val error = GroupUiError.mapFrom(throwable)
            Effect.ShowError(error.message)
        }
    }

    private fun onGroupUpdated(group: GroupUiModel) = setEditingGroup(group)

    private fun onMemberSelectionChange(user: GroupMemberUiModel?) =
        setSelectedMember(user?.uid)
}

