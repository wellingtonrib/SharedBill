package br.com.jwar.sharedbill.groups.presentation.ui.payment

import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.usecases.CreatePaymentUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.GetGroupByIdUseCase
import br.com.jwar.sharedbill.groups.domain.usecases.SendPaymentUseCase
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
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

class GroupPaymentViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val stringProvider: StringProvider = mockk()
    private val sendPaymentUseCase: SendPaymentUseCase = mockk()
    private val createPaymentUseCase: CreatePaymentUseCase = mockk()
    private val getGroupByIdUseCase: GetGroupByIdUseCase = mockk()
    private val groupToGroupUiModelMapper: GroupToGroupUiModelMapper = mockk()

    private val viewModel: GroupPaymentViewModel by lazy {
        GroupPaymentViewModel(
            stringProvider = stringProvider,
            getGroupByIdUseCase = getGroupByIdUseCase,
            createPaymentUseCase = createPaymentUseCase,
            sendPaymentUseCase = sendPaymentUseCase,
            groupToGroupUiModelMapper = groupToGroupUiModelMapper
        )
    }

    @Test
    fun `onInit with Expense payment type should update ui state`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val paymentType = PaymentType.EXPENSE
        val group = Group()
        val firstMember = GroupMemberUiModel(UUID.randomUUID().toString())
        val secondMember = GroupMemberUiModel(UUID.randomUUID().toString())
        val members = ImmutableSet.of(firstMember, secondMember)
        val paidTo = members.associateWith { 1 }
        val groupUiModel = GroupUiModel(id = groupId, members = members)
        val stateList = mutableListOf<GroupPaymentContract.State>()
        val timeInMillis = 1000L
        prepareScenario(
            groupResult = Result.success(group),
            groupUiModel = groupUiModel,
            stateList = stateList,
            timeInMillis = timeInMillis
        )

        viewModel.emitEvent { GroupPaymentContract.Event.OnInit(groupId, paymentType) }

        with(viewModel.uiState.value) {
            assertEquals(groupUiModel, this.groupUiModel)
            assertFalse(this.isLoading)
            assertEquals(paymentType, this.paymentType)
            assertEquals(this.inputFields.size, 5)
            this.inputFields.forEach { field ->
                assertTrue(field.visible)
                assertFalse(field.hasError)
                when (field) {
                    is GroupPaymentContract.Field.DescriptionField -> {
                        assertEquals("", field.value)
                    }
                    is GroupPaymentContract.Field.ValueField -> {
                        assertEquals("", field.value)
                    }
                    is GroupPaymentContract.Field.DateField -> {
                        assertEquals(timeInMillis, field.value)
                    }
                    is GroupPaymentContract.Field.PaidByField -> {
                        assertEquals(firstMember, field.value)
                    }
                    is GroupPaymentContract.Field.PaidToField -> {
                        assertEquals(paidTo, field.value)
                    }
                }
            }
        }
    }

    @Test
    fun `onInit with Settlement payment type should update ui state`() = runTest {
        val groupId = UUID.randomUUID().toString()
        val paymentType = PaymentType.SETTLEMENT
        val group = Group()
        val firstMember = GroupMemberUiModel(UUID.randomUUID().toString())
        val secondMember = GroupMemberUiModel(UUID.randomUUID().toString())
        val members = ImmutableSet.of(firstMember, secondMember)
        val paidTo = ImmutableMap.of(secondMember, 1)
        val groupUiModel = GroupUiModel(id = groupId, members = members)
        val stateList = mutableListOf<GroupPaymentContract.State>()
        val timeInMillis = 1000L
        prepareScenario(
            groupResult = Result.success(group),
            groupUiModel = groupUiModel,
            stateList = stateList,
            timeInMillis = timeInMillis
        )

        viewModel.emitEvent { GroupPaymentContract.Event.OnInit(groupId, paymentType) }

        with(viewModel.uiState.value) {
            assertEquals(groupUiModel, this.groupUiModel)
            assertFalse(this.isLoading)
            assertEquals(paymentType, this.paymentType)
            assertEquals(this.inputFields.size, 5)
            this.inputFields.forEach { field ->
                assertFalse(field.hasError)
                when (field) {
                    is GroupPaymentContract.Field.DescriptionField -> {
                        assertEquals("Settlement", field.value)
                        assertFalse(field.visible)
                    }
                    is GroupPaymentContract.Field.ValueField -> {
                        assertEquals("", field.value)
                        assertTrue(field.visible)
                    }
                    is GroupPaymentContract.Field.DateField -> {
                        assertEquals(timeInMillis, field.value)
                        assertFalse(field.visible)
                    }
                    is GroupPaymentContract.Field.PaidByField -> {
                        assertEquals(firstMember, field.value)
                        assertTrue(field.visible)
                    }
                    is GroupPaymentContract.Field.PaidToField -> {
                        assertEquals(paidTo, field.value)
                        assertTrue(field.visible)
                    }
                }
            }
        }
    }

    @Test
    fun `onInit with error should set error state`() = Unit

    @Test
    fun `onDescriptionChange should update Description field on state`() = runTest {
        prepareScenario(isInitialized = true)

        viewModel.emitEvent { GroupPaymentContract.Event.OnDescriptionChange("description") }

        with(viewModel.uiState.value) {
            assertEquals(
                "description",
                this.inputFields.first { it is GroupPaymentContract.Field.DescriptionField }.value
            )
        }
    }

    @Test
    fun `onValueChange should update Value and SharedValue fields on state`() = Unit

    @Test
    fun `onDateChange should update Date field on state`() = Unit

    @Test
    fun `onPaidByChange should update PaidBy and PaidTo fields on state`() = Unit

    @Test
    fun `onPaidToChange should update PaidTo and SharedValue fields on state`() = Unit

    @Test
    fun `onSavePayment with success should call send payment use case`() = Unit

    @Test
    fun `onSavePayment with error should set error state`() = Unit

    @Test
    fun `onSendPayment with success should send Finish effect`() = Unit

    @Test
    fun `onSendPayment with error should set error state`() = Unit

    @Suppress("LongParameterList")
    private fun TestScope.prepareScenario(
        stateList: MutableList<GroupPaymentContract.State> = mutableListOf(),
        effectList: MutableList<GroupPaymentContract.Effect> = mutableListOf(),
        groupResult: Result<Group> = Result.success(Group()),
        groupId: String = UUID.randomUUID().toString(),
        paymentType: PaymentType = PaymentType.EXPENSE,
        groupUiModel: GroupUiModel = GroupUiModel(id = groupId),
        timeInMillis: Long = 0,
        isInitialized: Boolean = false,
    ) {
        coEvery { getGroupByIdUseCase(any(), any()) } returns groupResult
        coEvery { groupToGroupUiModelMapper.mapFrom(any()) } returns groupUiModel
        every { stringProvider.getString(R.string.label_settlement) } returns "Settlement"

        mockkStatic(Calendar::class)
        every { Calendar.getInstance().timeInMillis } returns timeInMillis

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEffect.toList(effectList)
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateList)
        }

        if (isInitialized) {
            viewModel.emitEvent { GroupPaymentContract.Event.OnInit(groupId, paymentType) }
        }
    }
}
