package br.com.jwar.sharedbill

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.account.domain.services.AuthService
import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.di.AppModule
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.testing.TestTags
import br.com.jwar.sharedbill.testing.fakes.FakeFactory
import br.com.jwar.sharedbill.testing.fakes.FakeGroupRepository
import br.com.jwar.sharedbill.testing.fakes.FakeUserRepository
import com.google.firebase.appcheck.AppCheckProviderFactory
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID
import br.com.jwar.sharedbill.account.data.di.DataModule as AccountDataModule
import br.com.jwar.sharedbill.groups.data.di.DataModule as GroupDataModule

@UninstallModules(AppModule::class, AccountDataModule::class, GroupDataModule::class)
@HiltAndroidTest
class NewPaymentEndToEndTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue @JvmField
    val stringProvider: StringProvider = mockk()

    @BindValue @JvmField
    val appCheckProviderFactory: AppCheckProviderFactory = mockk()

    @BindValue @JvmField
    val authService: AuthService = mockk()

    @BindValue @JvmField
    val userRepository: UserRepository = FakeUserRepository().apply {
        runTest { saveUser(FakeFactory.makeUser()) }
    }

    @BindValue @JvmField
    val groupRepository: GroupRepository = FakeGroupRepository().apply {
        runTest {
            val groupId = UUID.randomUUID().toString()
            val owner = userRepository.getCurrentUser()
            val member = FakeFactory.makeUser()
            createGroup(FakeFactory.makeGroup(id = groupId, title = "Group Test", owner = owner))
            addMember(member, groupId)
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun addNewExpense() = runTest {
        composeTestRule.onNodeWithText("Group Test")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithContentDescription("New payment").performClick()
        composeTestRule.onNodeWithContentDescription("New expense").performClick()
        composeTestRule.onNodeWithText("Description").performTextInput("Bill Test")
        composeTestRule.onNodeWithText("Value").performTextInput("100")
        composeTestRule.onNodeWithContentDescription("Done action").performClick()

        composeTestRule.onNodeWithTag(TestTags.GroupDetailContent).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.PaymentList)
            .onChildren()
            .onFirst()
            .assert(hasText("Bill Test") and hasText("$100.00"))
    }
}

