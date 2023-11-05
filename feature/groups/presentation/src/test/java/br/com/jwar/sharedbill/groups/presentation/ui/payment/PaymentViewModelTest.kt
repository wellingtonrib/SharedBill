package br.com.jwar.sharedbill.groups.presentation.ui.payment

import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.usecases.CreatePaymentUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.SendPaymentUseCase
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import com.google.common.collect.ImmutableSet
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.Calendar
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class PaymentViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val stringProvider: StringProvider = mockk()
    private val sendPaymentUseCase: SendPaymentUseCase = mockk()
    private val createPaymentUseCase: CreatePaymentUseCase = mockk()
    private val getGroupByIdUseCase: GetGroupByIdUseCase = mockk()
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper = mockk()

    private val viewModel: PaymentViewModel by lazy {
        PaymentViewModel(
            stringProvider = stringProvider,
            getGroupByIdUseCase = getGroupByIdUseCase,
            createPaymentUseCase = createPaymentUseCase,
            sendPaymentUseCase = sendPaymentUseCase,
            groupToGroupUiModelMapper = groupToGroupUiModelMapper
        )
    }

    @Test
    fun `onInit should get group, map and update ui state`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val paymentType = PaymentType.EXPENSE
        val group = Group()
        val firstMember = GroupMemberUiModel(UUID.randomUUID().toString())
        val secondMember = GroupMemberUiModel(UUID.randomUUID().toString())
        val members = ImmutableSet.of(firstMember, secondMember)
        val groupUiModel = GroupUiModel(id = groupId, members = members)
        val stateList = mutableListOf<PaymentContract.State>()
        val timeInMillis = 1000L
        prepareScenario(
            groupResult = Result.success(group),
            groupUiModel = groupUiModel,
            stateList = stateList,
            timeInMillis = timeInMillis
        )

        viewModel.emitEvent { PaymentContract.Event.OnInit(groupId, paymentType) }

        coVerify { getGroupByIdUseCase(groupId) }
        verify { groupToGroupUiModelMapper.mapFrom(group) }
        with(viewModel.uiState.value) {
            assertEquals(groupUiModel, this.groupUiModel)
            assertFalse(this.isLoading)
            assertEquals(paymentType, this.paymentType)
            this.inputFields.forEach { field ->
                assertTrue(field.visible)
                assertFalse(field.hasError)
                assertEquals(
                    when(field) {
                        is PaymentContract.Field.DescriptionField -> ""
                        is PaymentContract.Field.ValueField -> ""
                        is PaymentContract.Field.DateField -> timeInMillis
                        is PaymentContract.Field.PaidByField -> firstMember
                        is PaymentContract.Field.PaidToField -> members
                    },
                    field.value
                )
            }
        }
    }

    @Test
    fun `onInit with error should set error state`() = runTest {

    }

    @Test
    fun `onDescriptionChange should update Description field on state`() = runTest {

    }

    @Test
    fun `onValueChange should update Value and SharedValue fields on state`() = runTest {

    }

    @Test
    fun `onDateChange should update Date field on state`() = runTest {

    }

    @Test
    fun `onPaidByChange should update PaidBy and PaidTo fields on state`() = runTest {

    }

    @Test
    fun `onPaidToChange should update PaidTo and SharedValue fields on state`() = runTest {

    }

    @Test
    fun `onSavePayment with success should call send payment use case`() = runTest {

    }

    @Test
    fun `onSavePayment with error should set error state`() = runTest {

    }

    @Test
    fun `onSendPayment with success should send Finish effect`() = runTest {

    }

    @Test
    fun `onSendPayment with error should set error state`() = runTest {

    }

    private fun TestScope.prepareScenario(
        stateList: MutableList<PaymentContract.State> = mutableListOf(),
        effectList: MutableList<PaymentContract.Effect> = mutableListOf(),
        groupResult: Result<Group> = Result.success(Group()),
        groupUiModel: GroupUiModel = GroupUiModel(),
        timeInMillis: Long = 0,
    ) {
        coEvery { getGroupByIdUseCase(any(), any()) } returns groupResult
        coEvery { groupToGroupUiModelMapper.mapFrom(any()) } returns groupUiModel

        mockkStatic(Calendar::class)
        every { Calendar.getInstance().timeInMillis } returns timeInMillis

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEffect.toList(effectList)
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateList)
        }
    }
}