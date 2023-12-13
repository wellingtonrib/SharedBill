package br.com.jwar.sharedbill.groups.presentation.ui.group_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.util.UiText
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.testing.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GroupListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun groupListScreen_withLoadingState_shouldShowLoadingContent() {
        prepareScenario(GroupListContract.State.Loading)

        composeTestRule.onNodeWithTag(TestTags.LoadingContent).assertIsDisplayed()
    }

    @Test
    fun groupListScreen_withLoadedState_shouldShowGroupListContent() {
        prepareScenario(GroupListContract.State.Loaded(listOf(GroupUiModel.sample())))

        composeTestRule.onNodeWithTag(TestTags.GroupListContent).assertIsDisplayed()
    }

    @Test
    fun groupListScreen_withLoadedStateEmpty_shouldShowGroupListContent() {
        prepareScenario(GroupListContract.State.Loaded(emptyList()))

        composeTestRule.onNodeWithTag(TestTags.GroupListContent).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.EmptyContent).assertIsDisplayed()
    }

    @Test
    fun groupListScreen_withErrorState_shouldShowErrorContent() {
        prepareScenario(GroupListContract.State.Error(UiText.DynamicString("Error"), GroupListContract.Event.OnInit))

        composeTestRule.onNodeWithTag(TestTags.ErrorContent).assertIsDisplayed()
    }

    private fun prepareScenario(
        state: GroupListContract.State
    ) {
        composeTestRule.setContent {
            SharedBillTheme {
                GroupListScreen(state = state)
            }
        }
    }

}