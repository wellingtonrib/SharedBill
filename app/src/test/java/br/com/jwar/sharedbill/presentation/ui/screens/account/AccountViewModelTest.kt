package br.com.jwar.sharedbill.presentation.ui.screens.account

import br.com.jwar.sharedbill.CoroutinesTestRule
import br.com.jwar.sharedbill.Fakes
import br.com.jwar.sharedbill.domain.exceptions.UserException
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.domain.usecases.GetCurrentUserUseCase
import br.com.jwar.sharedbill.domain.usecases.SignOutUseCase
import br.com.jwar.sharedbill.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.presentation.screens.account.AccountContract
import br.com.jwar.sharedbill.presentation.screens.account.AccountViewModel
import io.mockk.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

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
            userToUserUiModelMapper = userToUserUiModelMapper
        )
    }

    @Test
    fun `GIVEN viewModel WHEN GetUser event SHOULD call getUserCase`() = runTest {
        //GIVEN
        coEvery { getCurrentUserUseCase() } returns Result.success(User())
        //WHEN
        viewModel.emitEvent { AccountContract.Event.OnInit }
        //THEN
        coVerify { getCurrentUserUseCase() }
    }

    @Test
    fun `GIVEN viewModel WHEN GetUser event SHOULD update the ui state as Loading`() = runTest {
        //GIVEN
        coEvery { getCurrentUserUseCase() } returns Result.success(User())
        //WHEN
        viewModel.emitEvent { AccountContract.Event.OnInit }
        //THEN
        val state = viewModel.uiState.value as? AccountContract.State.Loading
        assertNotNull(state)
    }

    @Test
    fun `GIVEN there is an User WHEN GetUser event SHOULD update the ui state as Loaded with User`() = runTest {
        //GIVEN
        val user = Fakes.user
        coEvery { getCurrentUserUseCase() } returns Result.success(user)
        //WHEN
        viewModel.emitEvent { AccountContract.Event.OnInit }
        //THEN
        val state = viewModel.uiState.value as? AccountContract.State.Loaded
        assertNotNull(state)
    }

    @Test
    fun `GIVEN there is an exception WHEN GetUser event SHOULD update the ui state as Error with message`() = runTest {
        //GIVEN
        val exception = Exception("Generic Exception")
        coEvery { getCurrentUserUseCase() } returns Result.failure(UserException.UserNotFoundException)
        //WHEN
        viewModel.emitEvent { AccountContract.Event.OnInit }
        //THEN
        val state = viewModel.uiState.value as? AccountContract.State.Error
        assertNotNull(state)
        assertEquals(exception.message, state.message)
    }

    @Test
    fun `GIVEN a UserNotFoundException WHEN GetUser event SHOULD send effect GoToAuth`() = runTest {
        //GIVEN
        coEvery { getCurrentUserUseCase() } returns Result.failure(UserException.UserNotFoundException)
        //WHEN
        viewModel.emitEvent { AccountContract.Event.OnInit }
        //THEN
        coroutineTestRule.scope.launch {
            assertEquals(AccountContract.Effect.GoToAuth, viewModel.uiEffect.last())
        }
    }

    @Test
    fun `GIVEN viewModel WHEN SignOut event SHOULD call signOutUseCase`() = runTest {
        //GIVEN
        coEvery { signOutUseCase() } returns mockk(relaxed = true)
        //WHEN
        viewModel.emitEvent { AccountContract.Event.OnSignOut }
        //THEN
        coVerify { signOutUseCase() }
    }

    @Test
    fun `GIVEN successful signOut WHEN SignOut event SHOULD send effect GoToAuth`() = runTest {
        //GIVEN
        coEvery { signOutUseCase() } returns mockk(relaxed = true)
        //WHEN
        viewModel.emitEvent { AccountContract.Event.OnSignOut }
        //THEN
        coroutineTestRule.scope.launch {
            assertEquals(AccountContract.Effect.GoToAuth, viewModel.uiEffect.last())
        }
    }
}