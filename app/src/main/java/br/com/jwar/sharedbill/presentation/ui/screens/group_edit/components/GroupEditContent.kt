package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent

@Composable
fun GroupEditContent(
    state: State,
    snackHostState: SnackbarHostState = SnackbarHostState(),
    onSaveGroupClick: (GroupUiModel) -> Unit = {},
    onSaveMemberClick: (String) -> Unit = {},
    onMemberSelectionChange: (UserUiModel?) -> Unit = {},
    onMemberDeleteClick: (String) -> Unit = {},
) {
    when (state) {
        is State.Loading -> LoadingContent()
        is State.Editing -> GroupEditForm(
            state = state,
            onSaveGroupClick = onSaveGroupClick,
            onSaveMemberClick = onSaveMemberClick,
            onMemberSelectionChange = onMemberSelectionChange,
            onMemberDeleteClick = onMemberDeleteClick
        )
    }

    SnackbarHost(
        hostState = snackHostState,
        modifier = Modifier.fillMaxWidth().wrapContentHeight(Alignment.Bottom)
    )
}

@Preview
@Composable
fun previewGroupEditContent() {
    SharedBillTheme {
        Scaffold {
            GroupEditContent(
                state = State.Editing(GroupUiModel.sample()),
            )
        }
    }
}