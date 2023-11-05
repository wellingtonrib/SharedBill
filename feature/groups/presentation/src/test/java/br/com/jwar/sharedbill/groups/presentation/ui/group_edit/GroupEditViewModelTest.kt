package br.com.jwar.sharedbill.groups.presentation.ui.group_edit

import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.usecases.AddMemberUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.RemoveMemberUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.UpdateGroupUseCase
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiError
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import com.google.common.collect.ImmutableSet
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs

class GroupEditViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val getGroupByIdUseCase: GetGroupByIdUseCase = mockk()
    private val addMemberUseCase: AddMemberUseCase = mockk()
    private val updateGroupUseCase: UpdateGroupUseCase = mockk()
    private val removeMemberUseCase: RemoveMemberUseCase = mockk()
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper = mockk()

    private val viewModel: GroupEditViewModel by lazy {
        GroupEditViewModel(
            getGroupByIdUseCase = getGroupByIdUseCase,
            addMemberUseCase = addMemberUseCase,
            updateGroupUseCase = updateGroupUseCase,
            removeMemberUseCase = removeMemberUseCase,
            groupToGroupUiModelMapper = groupToGroupUiModelMapper
        )
    }

    @Test
    fun onInit_withSuccess_shouldSetEditingGroupState() = runTest {
        val groupId = UUID.randomUUID().toString()
        val group = Group()
        val groupUiModel = GroupUiModel()
        prepareScenario(getGroupResult = Result.success(group), groupUiModel = groupUiModel,)

        viewModel.emitEvent { GroupEditContract.Event.OnInit(groupId) }

        coVerify { getGroupByIdUseCase(groupId) }
        verify { groupToGroupUiModelMapper.mapFrom(group) }
        assertEquals(groupUiModel, viewModel.uiState.value.uiModel)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun onInit_withSuccessAndSelectedMember_shouldSetEditingGroupStateWithSelectedMember() = runTest {
        val groupId = UUID.randomUUID().toString()
        val group = Group()
        val selectedMemberId = UUID.randomUUID().toString()
        val selectedMember = GroupMemberUiModel(selectedMemberId)
        val groupUiModel = GroupUiModel(members = ImmutableSet.of(selectedMember))
        prepareScenario(getGroupResult = Result.success(group), groupUiModel = groupUiModel,)

        viewModel.emitEvent { GroupEditContract.Event.OnInit(groupId, selectedMemberId) }

        assertEquals(selectedMember, viewModel.uiState.value.selectedMember)
    }

    @Test
    fun onInit_withError_shouldSendErrorEffect() = runTest {
        val exception = Exception()
        val error =  GroupUiError.GroupGenericError
        prepareScenario(getGroupResult = Result.failure(exception), groupUiError = error)

        viewModel.emitEvent { GroupEditContract.Event.OnInit("") }

        assertFalse(viewModel.uiState.value.isLoading)
        assertIs<GroupEditContract.Effect.ShowError>(viewModel.uiEffect.first())
    }

    @Test
    fun onSaveGroup_withSuccessWithFinishFlag_shouldSendSuccessEffect() {

    }

    @Test
    fun onSaveGroup_withSuccessWithoutFinishFlag_shouldNotSendSuccessEffect() {

    }

    @Test
    fun onSaveGroup_withError_shouldSendErrorEffect() {

    }

    @Test
    fun onSaveMember_withSuccess_shouldEmitOnInitEvent() {

    }

    @Test
    fun onSaveMember_withError_shouldSendErrorEffect() {

    }

    @Test
    fun onDeleteMember_withSuccess_shouldEmitOnInitEvent() {

    }

    @Test
    fun onDeleteMember_withError_shouldSendErrorEffect() {

    }

    private fun prepareScenario(
        getGroupResult: Result<Group> = Result.success(Group()),
        addMemberResult: Result<String> = Result.success(""),
        updateGroupResult: Result<Unit> = Result.success(Unit),
        removeUserResult: Result<Unit> = Result.success(Unit),
        groupUiModel: GroupUiModel = GroupUiModel(),
        groupUiError: GroupUiError = GroupUiError.GroupGenericError,
    ) {
        mockkObject(GroupUiError.Companion)
        every { GroupUiError.Companion.mapFrom(any()) } returns groupUiError
        coEvery { getGroupByIdUseCase(any()) } returns getGroupResult
        coEvery { addMemberUseCase(any(), any()) } returns addMemberResult
        coEvery { updateGroupUseCase(any(), any()) } returns updateGroupResult
        coEvery { removeMemberUseCase(any(), any()) } returns removeUserResult
        every { groupToGroupUiModelMapper.mapFrom(any()) } returns groupUiModel
    }
}