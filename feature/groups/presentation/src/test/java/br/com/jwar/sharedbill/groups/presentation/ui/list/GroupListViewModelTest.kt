package br.com.jwar.sharedbill.groups.presentation.ui.list

import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.usecases.CreateGroupUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.DeleteGroupUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupsStreamUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.JoinGroupUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.LeaveGroupUseCase
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull

class GroupListViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val getGroupsStreamUseCase: GetGroupsStreamUseCase = mockk()
    private val createGroupUseCase: CreateGroupUseCase = mockk()
    private val joinGroupUseCase: JoinGroupUseCase = mockk()
    private val deleteGroupUseCase: DeleteGroupUseCase = mockk()
    private val leaveGroupUseCase: LeaveGroupUseCase = mockk()
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper = mockk()

    private val viewModel: GroupListViewModel by lazy {
        GroupListViewModel(
            getGroupsStreamUseCase = getGroupsStreamUseCase,
            createGroupUseCase = createGroupUseCase,
            joinGroupUseCase = joinGroupUseCase,
            deleteGroupUseCase = deleteGroupUseCase,
            leaveGroupUseCase = leaveGroupUseCase,
            groupToGroupUiModelMapper = groupToGroupUiModelMapper
        )
    }

    @Test
    fun `onInit should call get groups stream, map and set loaded state`() = runTest {
        val group = Group()
        val groupResult = Result.success(listOf(group))
        val groupUiModel = GroupUiModel()
        prepareScenario(groupResult = groupResult, groupUiModel = groupUiModel)

        viewModel.emitEvent { GroupListContract.Event.OnInit }

        val state = viewModel.uiState.value as? GroupListContract.State.Loaded
        coVerify { getGroupsStreamUseCase() }
        verify { groupToGroupUiModelMapper.mapFrom(group) }
        assertNotNull(state)
        assertEquals(listOf(groupUiModel), state.uiModel)
    }

    @Test
    fun `onInit with error should set error state`() = Unit

    @Test
    fun `onCreateGroup with success should send NavigateToGroupEdit effect`() = Unit

    @Test
    fun `onCreateGroup with error should set error state`() = Unit

    @Test
    fun `onJoinGroup with success should send NavigateToGroupDetails effect`() = Unit

    @Test
    fun `onJoinGroup with error should set error state`() = Unit

    @Test
    fun `onDeleteGroup with success should update loaded state removing the group`() = Unit

    @Test
    fun `onDeleteGroup with error should send ShowError effect`() = Unit

    @Test
    fun `onLeaveGroup with success should update loaded state removing the group`() = Unit

    @Test
    fun `onLeaveGroup with error should send ShowError effect`() = Unit

    @Suppress("LongParameterList")
    private fun TestScope.prepareScenario(
        groupResult: Result<List<Group>> = Result.success(listOf(Group())),
        groupUiModel: GroupUiModel = GroupUiModel(),
        createGroupResult: Result<String> = Result.success(""),
        joinGroupResult: Result<String> = Result.success(""),
        deleteGroupResult: Result<Unit> = Result.success(Unit),
        leaveGroupResult: Result<Unit> = Result.success(Unit),
        effectList: MutableList<GroupListContract.Effect> = mutableListOf(),
        stateList: MutableList<GroupListContract.State> = mutableListOf(),
    ) {
        coEvery { getGroupsStreamUseCase() } returns flow {
            emit(groupResult)
        }
        every { groupToGroupUiModelMapper.mapFrom(any()) } returns groupUiModel
        coEvery { createGroupUseCase(any()) } returns createGroupResult
        coEvery { joinGroupUseCase(any()) } returns joinGroupResult
        coEvery { deleteGroupUseCase(any()) } returns deleteGroupResult
        coEvery { leaveGroupUseCase(any()) } returns leaveGroupResult

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateList)
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEffect.toList(effectList)
        }
    }
}
