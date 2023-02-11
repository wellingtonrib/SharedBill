package br.com.jwar.sharedbill.presentation.screens.group_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.usecases.AddMemberUseCase
import br.com.jwar.sharedbill.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.domain.usecases.RemoveMemberUseCase
import br.com.jwar.sharedbill.domain.usecases.UpdateGroupUseCase
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.models.GroupUiError
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.navigation.AppDestinationsArgs
import br.com.jwar.sharedbill.presentation.screens.group_edit.GroupEditContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val addMemberUseCase: AddMemberUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val removeMemberUseCase: RemoveMemberUseCase,
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
): BaseViewModel<Event, State, Effect>() {

    private val groupId: String = checkNotNull(savedStateHandle[AppDestinationsArgs.GROUP_ID_ARG])

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

    private fun getEditingGroup() = uiState.value.uiModel ?: GroupUiModel()

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

    private fun setEditingGroup(group: GroupUiModel) =
        setState { it.copy(isLoading = false, uiModel = group) }

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