package br.com.jwar.groups.presentation.screens.group_list.components

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.groups.presentation.screens.group_list.GroupListContract.State
import br.com.jwar.sharedbill.core.designsystem.components.ErrorContent
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun GroupListScreen(
    state: State,
    onGroupClick: (groupId: String) -> Unit = {},
    onGroupCreate: (title: String) -> Unit = {},
    onGroupJoin: (inviteCode: String) -> Unit = {},
    onGroupDelete: (groupId: String) -> Unit = {},
    onGroupLeave: (groupId: String) -> Unit = {},
    onTryAgainClick: () -> Unit = {},
) {
    when(state) {
        is State.Loading -> LoadingContent()
        is State.Loaded -> GroupList(
            groups = state.uiModel,
            onGroupClick = onGroupClick,
            onGroupCreate = onGroupCreate,
            onGroupJoin = onGroupJoin,
            onGroupDelete = onGroupDelete,
            onGroupLeave = onGroupLeave
        )
        is State.Error -> ErrorContent(state.message.orEmpty(), onAction = onTryAgainClick)
    }
}

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