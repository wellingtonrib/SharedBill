package br.com.jwar.groups.presentation.screens.group_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapper
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
            is Event.OnSaveGroup -> onSaveGroupClick()
            is Event.OnSaveMember -> onSaveMemberClick(event.userName)
            is Event.OnMemberSelectionChange -> onMemberSelect(event.user)
            is Event.OnMemberDelete -> onMemberDeleteClick(event.userId)
            is Event.OnGroupUpdated -> onGroupUpdated(event.group)
        }
    }

    private fun onInit() = viewModelScope.launch {
        getGroupByIdUseCase(groupId, false)
            .onSuccess { setEditingGroup(it) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onSaveGroupClick() = viewModelScope.launch {
        val groupEdited = getEditingGroup()
        setLoadingState()
        updateGroupUseCase(groupEdited.id, groupEdited.title)
            .onSuccess { sendSuccessEffect() }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onSaveMemberClick(userName: String) = viewModelScope.launch {
        setLoadingState()
        addMemberUseCase(userName, groupId)
            .onSuccess { setShouldSelectMemberState(userName) }
            .onFailure { sendErrorEffect(it) }
    }

    private fun onMemberDeleteClick(userId: String) = viewModelScope.launch {
        setLoadingState()
        removeMemberUseCase(userId, groupId)
            .onSuccess { sendSuccessEffect() }
            .onFailure { sendErrorEffect(it) }
    }

    private fun setLoadingState() = setState { it.copy(isLoading = true) }

    private fun getEditingGroup() = uiState.value.uiModel ?: br.com.jwar.groups.presentation.models.GroupUiModel()

    private fun setEditingGroup(group: Group) =
        setState {
            val groupUiModel = groupToGroupUiModelMapper.mapFrom(group)
            val shouldSelectMemberName = it.shouldSelectMemberByName
            val selectedMember = groupUiModel.members.firstOrNull { member ->
                member.name == shouldSelectMemberName
            }
            it.copy(
                isLoading = false,
                uiModel = groupUiModel,
                selectedMember = selectedMember,
                shouldSelectMemberByName = null
            )
        }

    private fun setEditingGroup(group: br.com.jwar.groups.presentation.models.GroupUiModel) =
        setState { it.copy(isLoading = false, uiModel = group) }

    private fun sendSuccessEffect() {
        setState { it.copy(isLoading = false) }
        sendEffect { Effect.ShowSuccess }
    }

    private fun sendErrorEffect(throwable: Throwable) {
        val error = br.com.jwar.groups.presentation.models.GroupUiError.mapFrom(throwable)
        setState { it.copy(isLoading = false) }
        sendEffect { Effect.ShowError(error.message) }
    }

    private fun setShouldSelectMemberState(userName: String) =
        setState { it.copy(shouldSelectMemberByName = userName) }

    private fun onGroupUpdated(group: br.com.jwar.groups.presentation.models.GroupUiModel) = setEditingGroup(group)

    private fun onMemberSelect(user: br.com.jwar.groups.presentation.models.GroupMemberUiModel?) = setState { it.copy(selectedMember = user) }
}