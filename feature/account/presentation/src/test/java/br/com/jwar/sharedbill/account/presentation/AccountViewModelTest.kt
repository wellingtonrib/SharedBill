package br.com.jwar.sharedbill.account.presentation


import br.com.jwar.sharedbill.account.domain.exceptions.UserException
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.usecases.*
import br.com.jwar.sharedbill.account.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.account.presentation.screens.AccountContract
import br.com.jwar.sharedbill.account.presentation.screens.AccountViewModel
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import br.com.jwar.sharedbill.testing.Fakes
import io.mockk.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class AccountViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val signOutUseCase = mockk<SignOutUseCase>()
    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>()
    private val signUpUseCase = mockk<SignUpUseCase>()
    private val signInUseCase = mockk<SignInUseCase>()
    private val signInFirebaseUseCase = mockk<SignInFirebaseUseCase>()
    private val userToUserUiModelMapper = mockk<UserToUserUiModelMapper>()
    private val viewModel: AccountViewModel by lazy {
        AccountViewModel(
            getCurrentUserUseCase = getCurrentUserUseCase,
            signUpUseCase = signUpUseCase,
            signOutUseCase = signOutUseCase,
            signInUseCase = signInUseCase,
            signInFirebaseUseCase = signInFirebaseUseCase,
            userToUserUiModelMapper = userToUserUiModelMapper,
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
        val user = Fakes.makeUser()
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
    fun `GIVEN viewModel WHEN SignOut event SHOULD call signOutUseCase`() = runTest {
        //GIVEN
        coEvery { signOutUseCase() } returns mockk(relaxed = true)
        //WHEN
        viewModel.emitEvent { AccountContract.Event.OnSignOut }
        //THEN
        coVerify { signOutUseCase() }
    }
}