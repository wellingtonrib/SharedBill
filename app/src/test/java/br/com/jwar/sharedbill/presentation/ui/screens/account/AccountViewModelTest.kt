package br.com.jwar.sharedbill.presentation.ui.screens.account

import br.com.jwar.sharedbill.CoroutinesTestRule
import br.com.jwar.sharedbill.Fakes
import br.com.jwar.sharedbill.domain.exceptions.UserNotFoundException
import br.com.jwar.sharedbill.domain.model.Resource
import br.com.jwar.sharedbill.domain.usecases.GetUserUseCase
import br.com.jwar.sharedbill.domain.usecases.SignOutUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
    private val getUserUseCase = mockk<GetUserUseCase>()
    private val viewModel: AccountViewModel by lazy {
        AccountViewModel(
            getUserUseCase = getUserUseCase,
            signOutUseCase = signOutUseCase
        )
    }

    @Test
    fun `GIVEN viewModel WHEN GetUser event SHOULD call getUserCase`() = runTest {
        //GIVEN
        coEvery { getUserUseCase() } returns flowOf()
        //WHEN
        viewModel.emitEvent { AccountContract.Event.GetUser }
        //THEN
        coVerify { getUserUseCase() }
    }

    @Test
    fun `GIVEN viewModel WHEN GetUser event SHOULD update the ui state as Loading`() = runTest {
        //GIVEN
        coEvery { getUserUseCase() } returns flowOf(Resource.Loading)
        //WHEN
        viewModel.emitEvent { AccountContract.Event.GetUser }
        //THEN
        val state = viewModel.uiState.value as? AccountContract.State.Loading
        assertNotNull(state)
    }

    @Test
    fun `GIVEN there is an User WHEN GetUser event SHOULD update the ui state as Loaded with User`() = runTest {
        //GIVEN
        val user = Fakes.user
        coEvery { getUserUseCase() } returns flowOf(Resource.Success(user))
        //WHEN
        viewModel.emitEvent { AccountContract.Event.GetUser }
        //THEN
        val state = viewModel.uiState.value as? AccountContract.State.Loaded
        assertNotNull(state)
        assertEquals(user, state.user)
    }

    @Test
    fun `GIVEN there is an exception WHEN GetUser event SHOULD update the ui state as Error with message`() = runTest {
        //GIVEN
        val exception = Exception("Generic Exception")
        coEvery { getUserUseCase() } returns flowOf(Resource.Failure(exception))
        //WHEN
        viewModel.emitEvent { AccountContract.Event.GetUser }
        //THEN
        val state = viewModel.uiState.value as? AccountContract.State.Error
        assertNotNull(state)
        assertEquals(exception.message, state.message)
    }

    @Test
    fun `GIVEN a UserNotFoundException WHEN GetUser event SHOULD send effect GoToAuth`() = runTest {
        //GIVEN
        val exception = UserNotFoundException()
        coEvery { getUserUseCase() } returns flowOf(Resource.Failure(exception))
        //WHEN
        viewModel.emitEvent { AccountContract.Event.GetUser }
        //THEN
        coroutineTestRule.scope.launch {
            assertEquals(AccountContract.Effect.GoToAuth, viewModel.uiEffect.last())
        }
    }

    @Test
    fun `GIVEN viewModel WHEN SignOut event SHOULD call signOutUseCase`() = runTest {
        //GIVEN
        coEvery { signOutUseCase() } returns flowOf()
        //WHEN
        viewModel.emitEvent { AccountContract.Event.SignOut }
        //THEN
        coVerify { signOutUseCase() }
    }

    @Test
    fun `GIVEN viewModel WHEN SignOut event SHOULD update the ui state as Loading`() = runTest {
        //GIVEN
        coEvery { signOutUseCase() } returns flowOf(Resource.Loading)
        //WHEN
        viewModel.emitEvent { AccountContract.Event.SignOut }
        //THEN
        val state = viewModel.uiState.value as? AccountContract.State.Loading
        assertNotNull(state)
    }

    @Test
    fun `GIVEN successful signOut WHEN SignOut event SHOULD send effect GoToAuth`() = runTest {
        //GIVEN
        coEvery { signOutUseCase() } returns flowOf(Resource.Success(true))
        //WHEN
        viewModel.emitEvent { AccountContract.Event.SignOut }
        //THEN
        coroutineTestRule.scope.launch {
            assertEquals(AccountContract.Effect.GoToAuth, viewModel.uiEffect.last())
        }
    }
}