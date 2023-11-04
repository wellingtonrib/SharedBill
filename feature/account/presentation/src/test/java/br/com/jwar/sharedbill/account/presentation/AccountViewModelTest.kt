package br.com.jwar.sharedbill.account.presentation


import br.com.jwar.sharedbill.account.domain.exceptions.UserException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.usecases.*
import br.com.jwar.sharedbill.account.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountViewModel
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import io.mockk.*
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class AccountViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val signOutUseCase = mockk<SignOutUseCase>()
    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>()
    private val userToUserUiModelMapper = mockk<UserToUserUiModelMapper>()
    private val viewModel: AccountViewModel by lazy {
        AccountViewModel(
            getCurrentUserUseCase = getCurrentUserUseCase,
            signOutUseCase = signOutUseCase,
            userToUserUiModelMapper = userToUserUiModelMapper,
        )
    }

    @Test
    fun `GIVEN a logged in user WHEN init SHOULD get current and map the user`() = runTest {
        val user = User()
        prepareScenario(userResult = Result.success(user))

        viewModel.emitEvent { AccountContract.Event.OnInit }

        coVerify { getCurrentUserUseCase() }
        verify { userToUserUiModelMapper.mapFrom(user) }
    }

    @Test
    fun `GIVEN a logged in user WHEN init SHOULD update the ui state`() = runTest {
        val userUiModel = UserUiModel()
        val stateList = mutableListOf<AccountContract.State>()
        prepareScenario(
            stateList = stateList,
            userUiModelResult = userUiModel
        )

        viewModel.emitEvent { AccountContract.Event.OnInit }

        assertTrue(stateList.first().isLoading)
        assertEquals(stateList.drop(1).first().uiModel, userUiModel)
    }

    @Test
    fun `GIVEN UserNotFoundException WHEN init SHOULD send NavigateToAuth effect`() = runTest {
        val effectList = mutableListOf<AccountContract.Effect>()
        prepareScenario(
            effectList = effectList,
            userResult = Result.failure(UserException.UserNotFoundException)
        )

        viewModel.emitEvent { AccountContract.Event.OnInit }

        assertEquals(AccountContract.Effect.NavigateToAuth, effectList.first())
    }

    @Test
    fun `GIVEN a logged in user WHEN SignOut event SHOULD call signOutUseCase`() = runTest {
        prepareScenario()

        viewModel.emitEvent { AccountContract.Event.OnSignOutClick }

        coVerify { signOutUseCase() }
    }

    @Test
    fun `GIVEN logged in user WHEN SignOut event SHOULD send NavigateToAuth effect`() = runTest {
        val effectList = mutableListOf<AccountContract.Effect>()
        prepareScenario(effectList = effectList)

        viewModel.emitEvent { AccountContract.Event.OnSignOutClick }

        assertEquals(AccountContract.Effect.NavigateToAuth, effectList.first())
    }

    private fun TestScope.prepareScenario(
        stateList: MutableList<AccountContract.State> = mutableListOf(),
        effectList: MutableList<AccountContract.Effect> = mutableListOf(),
        userResult: Result<User> = Result.success(User()),
        userUiModelResult: UserUiModel = UserUiModel(),
    ) {
        coEvery { getCurrentUserUseCase() } returns userResult
        every { userToUserUiModelMapper.mapFrom(any()) } returns userUiModelResult
        coEvery { signOutUseCase() } returns Result.success(Unit)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateList)
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEffect.toList(effectList)
        }
    }
}