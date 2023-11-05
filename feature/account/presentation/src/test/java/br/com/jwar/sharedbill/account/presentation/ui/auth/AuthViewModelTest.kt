package br.com.jwar.sharedbill.account.presentation.ui.auth

import br.com.jwar.sharedbill.account.domain.usecases.SignInFirebaseUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignInUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignUpUseCase
import br.com.jwar.sharedbill.testing.CoroutinesTestRule
import com.google.android.gms.auth.api.identity.BeginSignInResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

class AuthViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val signInUseCase: SignInUseCase = mockk()
    private val signUpUseCase: SignUpUseCase = mockk()
    private val signInFirebaseUseCase: SignInFirebaseUseCase = mockk()

    private val viewModel: AuthViewModel by lazy {
        AuthViewModel(
            signInUseCase = signInUseCase,
            signUpUseCase = signUpUseCase,
            signInFirebaseUseCase = signInFirebaseUseCase,
        )
    }

    @Test
    fun `onSignIn succeeded should send SignedIn effect`() = runTest {
        val stateList = mutableListOf<AuthContract.State>()
        val effectList = mutableListOf<AuthContract.Effect>()
        val beginSignInResult = mockk<BeginSignInResult>()
        prepareScenario(beginSignInResult = beginSignInResult, stateList = stateList, effectList = effectList)

        viewModel.emitEvent { AuthContract.Event.OnRequestSignIn }

        coVerify { signInUseCase() }
        assertIs<AuthContract.State.Loading>(stateList.last())
        assertIs<AuthContract.Effect.SignedIn>(effectList.last())
    }

    @Test
    fun `onSignIn failed should send ShowError effect`() = runTest {

    }

    @Test
    fun `onSignUp succeeded should send SignedIn effect`() = runTest {

    }

    @Test
    fun `onSignUp failed should send ShowError effect`() = runTest {

    }

    @Test
    fun `onSignInFirebase succeeded should send LoggedIn effect`() = runTest {

    }

    @Test
    fun `onSignInFirebase failed should send ShowError effect`() = runTest {

    }

    @Test
    fun `onSignInFirebaseFailed should set Idle state`() = runTest {

    }

    @Test
    fun `onPrivacyPolicyClick should send NavigateToPrivacyPolicy effect`() = runTest {

    }

    private fun TestScope.prepareScenario(
        stateList: MutableList<AuthContract.State> = mutableListOf(),
        effectList: MutableList<AuthContract.Effect> = mutableListOf(),
        beginSignInResult: BeginSignInResult = mockk(),
    ) {
        coEvery { signInUseCase() } returns Result.success(beginSignInResult)
        coEvery { signInFirebaseUseCase(any()) } returns Result.success(Unit)
        coEvery { signUpUseCase() } returns Result.success(beginSignInResult)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateList)
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEffect.toList(effectList)
        }
    }
}