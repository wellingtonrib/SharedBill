package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.generic_components.EmptyContent
import br.com.jwar.sharedbill.presentation.ui.generic_components.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium

@Composable
fun GroupListContent(
    state: State,
    onGroupCreate: (String) -> Unit = {},
    onGroupJoin: (String) -> Unit = {},
    onGroupClick: (groupId: String) -> Unit = {},
    onTryAgainClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidthPaddingMedium(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GroupListHeader(onGroupCreate, onGroupJoin)
        when(state) {
            is State.Loading -> LoadingContent()
            is State.Empty -> EmptyContent(message = stringResource(R.string.message_groups_empty))
            is State.Loaded -> GroupList(state.groups, onGroupClick)
            is State.Error -> ErrorContent(message = state.message.orEmpty(), onAction = onTryAgainClick)
        }
    }
}

@Preview
@Composable
fun PreviewGroupListContent() {
    SharedBillTheme {
        Scaffold {
            GroupListContent(
                state = State.Loaded(
                    listOf(
                        GroupUiModel.sample(),
                        GroupUiModel.sample(),
                        GroupUiModel.sample(),
                    )
                )
            )
        }
    }
}