package br.com.jwar.sharedbill.groups.presentation.ui.edit

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
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
    fun `onInit with success should set uiModel and isLoading to false`() = runTest {
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
    fun `onInit with selected member id should set selected member to the ui state`() = runTest {
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
    fun `onInit with error should send ShowError effect`() = runTest {
        val exception = Exception()
        val error = GroupUiError.GroupGenericError
        val effectList = mutableListOf<GroupEditContract.Effect>()
        prepareScenario(
            getGroupResult = Result.failure(exception),
            groupUiError = error,
            effectList = effectList
        )

        viewModel.emitEvent { GroupEditContract.Event.OnInit("") }

        assertFalse(viewModel.uiState.value.isLoading)
        assertIs<GroupEditContract.Effect.ShowError>(effectList.last())
    }

    @Test
    fun `onSaveGroup with success should send NavigateToGroupDetails effect`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val title = viewModel.uiState.value.uiModel?.title.orEmpty()
        val effectList = mutableListOf<GroupEditContract.Effect>()
        prepareScenario(updateGroupResult = Result.success(Unit), effectList = effectList)

        viewModel.emitEvent { GroupEditContract.Event.OnSaveGroup(groupId) }

        coVerify { updateGroupUseCase(groupId, title) }
        assertIs<GroupEditContract.Effect.NavigateToGroupDetails>(effectList.last())
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `onSaveGroup without finish flag should not send NavigateToGroupDetails effect`() = Unit

    @Test
    fun `onSaveGroup with error should send ShowError effect`() = Unit

    @Test
    fun `onSaveMember with success emit OnInit event`() = Unit

    @Test
    fun `onSaveMember with error should send ShowError effect`() = Unit

    @Test
    fun `onDeleteMember with success emit OnInit event`() = Unit

    @Test
    fun `onDeleteMember with error should send ShowError effect`() = Unit

    private fun TestScope.prepareScenario(
        effectList: MutableList<GroupEditContract.Effect> = mutableListOf(),
        getGroupResult: Result<Group> = Result.success(Group()),
        addMemberResult: Result<String> = Result.success(""),
        updateGroupResult: Result<Unit> = Result.success(Unit),
        removeUserResult: Result<Unit> = Result.success(Unit),
        groupUiModel: GroupUiModel = GroupUiModel(),
        groupUiError: GroupUiError = GroupUiError.GroupGenericError,
    ) {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEffect.toList(effectList)
        }
        mockkObject(GroupUiError.Companion)
        every { GroupUiError.Companion.mapFrom(any()) } returns groupUiError
        coEvery { getGroupByIdUseCase(any()) } returns getGroupResult
        coEvery { addMemberUseCase(any(), any()) } returns addMemberResult
        coEvery { updateGroupUseCase(any(), any()) } returns updateGroupResult
        coEvery { removeMemberUseCase(any(), any()) } returns removeUserResult
        every { groupToGroupUiModelMapper.mapFrom(any()) } returns groupUiModel
    }
}
