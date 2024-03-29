package br.com.jwar.sharedbill.groups.presentation.ui.list

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.components.ErrorContent
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.presentation.ui.list.GroupListContract.State
import br.com.jwar.sharedbill.groups.presentation.ui.list.components.GroupListContent

@Composable
@Suppress("LongParameterList")
fun GroupListScreen(
    state: State,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    onGroupClick: (groupId: String) -> Unit = {},
    onGroupCreate: (title: String) -> Unit = {},
    onGroupJoin: (inviteCode: String) -> Unit = {},
    onGroupDelete: (groupId: String) -> Unit = {},
    onGroupLeave: (groupId: String) -> Unit = {},
    onTryAgainClick: (GroupListContract.Event) -> Unit = {},
) {
    when (state) {
        is State.Loading -> LoadingContent()
        is State.Loaded -> GroupListContent(
            groups = state.uiModel,
            snackbarHostState = snackbarHostState,
            onGroupClick = onGroupClick,
            onGroupCreate = onGroupCreate,
            onGroupJoin = onGroupJoin,
            onGroupDelete = onGroupDelete,
            onGroupLeave = onGroupLeave
        )
        is State.Error -> ErrorContent(
            message = state.message.asString(),
            action = state.action.asString(),
            onAction = { onTryAgainClick(state.event) }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewGroupListContent() {
    SharedBillTheme {
        Scaffold {
            GroupListScreen(
                state = State.Loaded(
                    listOf(
                        GroupUiModel.sample(),
                        GroupUiModel.sample(),
                        GroupUiModel.sample(),
                    )
                ),
            )
        }
    }
}
