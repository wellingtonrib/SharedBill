package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsContract.State
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsContract.State.Error
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsContract.State.Loaded
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsContract.State.Loading
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.ErrorContent
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.groups.presentation.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun GroupDetailsScreen(
    state: State,
    onNavigateBack: () -> Unit = {},
    onNewPaymentClick: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onEditClick: () -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AppTopBar(
            navigationBack = onNavigateBack,
            title = stringResource(id = R.string.label_group_details),
            actions = {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Filled.Edit, stringResource(id = R.string.label_group_manage))
                }
            }
        )
        SwipeRefresh(
            state = rememberSwipeRefreshState(state is Loading),
            onRefresh = onRefresh
        ) {
            Column(modifier = Modifier.fillMaxWidthPaddingMedium()) {
                when(state) {
                    is Loading -> LoadingContent()
                    is Loaded -> GroupsDetails(
                        group = state.uiModel,
                        onNewPaymentClick = { onNewPaymentClick() }
                    )
                    is Error -> ErrorContent()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGroupDetailsContent() {
    SharedBillTheme {
        Scaffold {
            GroupDetailsScreen(state = Loaded(GroupUiModel.sample()))
        }
    }
}
