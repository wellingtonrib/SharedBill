package br.com.jwar.sharedbill.groups.presentation.ui.payment

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.testing.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PaymentScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun paymentScreen_withIsLoadingTrue_shouldShowLoadingContent() {
        prepareScenario(PaymentContract.State(isLoading = true))

        composeTestRule.onNodeWithTag(TestTags.LoadingContent).assertIsDisplayed()
    }

    @Test
    fun paymentScreen_withIsLoadingFalse_shouldShowPaymentContent() {
        prepareScenario(PaymentContract.State(isLoading = false))

        composeTestRule.onNodeWithTag(TestTags.PaymentContent).assertIsDisplayed()
    }

    private fun prepareScenario(
        state: PaymentContract.State
    ) {
        composeTestRule.setContent {
            SharedBillTheme {
                PaymentScreen(state = state)
            }
        }
    }

}