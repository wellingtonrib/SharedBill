package br.com.jwar.groups.presentation.screens.group_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.GroupUiError
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.groups.presentation.navigation.GROUP_ID_ARG
import br.com.jwar.groups.presentation.screens.group_edit.GroupEditContract.Effect
import br.com.jwar.groups.presentation.screens.group_edit.GroupEditContract.Event
import br.com.jwar.groups.presentation.screens.group_edit.GroupEditContract.State
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.usecases.AddMemberUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.RemoveMemberUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.UpdateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val addMemberUseCase: AddMemberUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val removeMemberUseCase: RemoveMemberUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
): BaseViewModel<Event, State, Effect>() {

    private val groupId: String = checkNotNull(savedStateHandle[GROUP_ID_ARG])

    init { onInit() }

    override fun getInitialState(): State = State(isLoading = true)

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnSaveGroup -> onSaveGroup()
            is Event.OnSaveMember -> onSaveMember(event.userName)
            is Event.OnMemberSelect -> onSelectMember(event.user)
            is Event.OnMemberDelete -> onDeleteMember(event.userId)
            is Event.OnGroupUpdated -> onGroupUpdated(event.group)
            is Event.OnShareInviteCode -> onShareInviteCode(event.inviteCode)
        }
    }

    private fun onInit(selectedMemberId: String? = null) = viewModelScope.launch {
        getGroupByIdUseCase(groupId)
            .onSuccess { setEditingGroup(it, selectedMemberId) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onSaveGroup() = viewModelScope.launch {
        val groupEdited = getEditingGroup()
        setLoadingState()
        updateGroupUseCase(groupEdited.id, groupEdited.title)
            .onSuccess { sendSuccessEffect() }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onSaveMember(userName: String) = viewModelScope.launch {
        setLoadingState()
        addMemberUseCase(userName, groupId)
            .onSuccess { onInit(it) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onDeleteMember(userId: String) = viewModelScope.launch {
        setLoadingState()
        removeMemberUseCase(userId, groupId)
            .onSuccess { sendSuccessEffect() }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onShareInviteCode(inviteCode: String) {
        sendEffect { Effect.ShareInviteCode(inviteCode) }
    }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun getEditingGroup() = uiState.value.uiModel ?: GroupUiModel()

    private fun setEditingGroup(group: Group, selectedMemberId: String?) =
        setState { state ->
            val groupUiModel = groupToGroupUiModelMapper.mapFrom(group)
            val selectedMember = selectedMemberId?.let {
                groupUiModel.members.firstOrNull { it.uid == selectedMemberId }
            }
            state.copy(
                isLoading = false,
                uiModel = groupUiModel,
                selectedMember = selectedMember,
            )
        }

    private fun setEditingGroup(group: GroupUiModel) =
        setState { it.copy(isLoading = false, uiModel = group) }

    private fun sendSuccessEffect() {
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

    private fun onSelectMember(user: GroupMemberUiModel?) =
        setState { it.copy(selectedMember = user) }
}