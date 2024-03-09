package br.com.jwar.sharedbill.groups.presentation.ui.details

import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.usecases.DeletePaymentUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdStreamUseCase
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
internal class GroupDetailsViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val getGroupByIdStreamUseCase: GetGroupByIdStreamUseCase = mockk()
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper = mockk()
    private val deletePaymentUseCase: DeletePaymentUseCase = mockk()
    private val stringProvider: StringProvider = mockk()
    private val viewModel: GroupDetailsViewModel by lazy {
        GroupDetailsViewModel(
            getGroupByIdStreamUseCase = getGroupByIdStreamUseCase,
            groupToGroupUiModelMapper = groupToGroupUiModelMapper,
            deletePaymentUseCase = deletePaymentUseCase,
            stringProvider = stringProvider,
        )
    }

    @Test
    fun `onInit should get group stream and map result`() = runTest {
        val group = Group()
        val groupId = UUID.randomUUID().toString()
        prepareScenario(groupResult = Result.success(group),)

        viewModel.emitEvent { GroupDetailsContract.Event.OnInit(groupId) }

        coVerify { getGroupByIdStreamUseCase(groupId) }
        verify { groupToGroupUiModelMapper.mapFrom(group) }
    }

    @Test
    fun `onInit succeeded should update ui state`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val groupUiModel = GroupUiModel()
        val stateList = mutableListOf<GroupDetailsContract.State>()
        prepareScenario(stateList = stateList, groupUiModelResult = groupUiModel)

        viewModel.emitEvent { GroupDetailsContract.Event.OnInit(groupId) }

        assertEquals(GroupDetailsContract.State.Loading, stateList.first())
        assertEquals(GroupDetailsContract.State.Loaded(groupUiModel), stateList.drop(1).first())
    }

    @Test
    fun `onInit failed should update ui state`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val exception = Exception()
        val stateList = mutableListOf<GroupDetailsContract.State>()
        prepareScenario(stateList = stateList, groupResult = Result.failure(exception))

        viewModel.emitEvent { GroupDetailsContract.Event.OnInit(groupId) }

        assertEquals(GroupDetailsContract.State.Loading, stateList.first())
        assertEquals(GroupDetailsContract.State.Error(exception.message.orEmpty()), stateList.drop(1).first())
    }

    @Test
    fun `onEditGroup should send NavigateToGroupEdit effect`() = Unit

    @Test
    fun `onNewPayment should send NavigateToNewPayment effect`() = Unit

    @Test
    fun `onShareBalance should send ShareBalance effect`() = Unit

    private fun TestScope.prepareScenario(
        stateList: MutableList<GroupDetailsContract.State> = mutableListOf(),
        groupResult: Result<Group> = Result.success(Group()),
        groupUiModelResult: GroupUiModel = GroupUiModel(),
    ) {
        coEvery { getGroupByIdStreamUseCase(any()) } returns flow {
            emit(groupResult)
        }
        every { groupToGroupUiModelMapper.mapFrom(any()) } returns groupUiModelResult
        every { stringProvider.getString(any()) } returns "string"
        coEvery { deletePaymentUseCase(any(), any(), any()) } returns Result.success(Unit)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateList)
        }
    }
}
