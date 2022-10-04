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
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent

@Composable
fun GroupEditContent(
    state: State,
    snackHostState: SnackbarHostState = SnackbarHostState(),
    onSaveGroupClick: (Group) -> Unit = {},
    onSaveMemberClick: (String, Group) -> Unit = { _,_-> },
) {
    when (state) {
        is State.Loading -> LoadingContent()
        is State.Editing -> GroupEditForm(
            group = state.group,
            onSaveGroupClick = onSaveGroupClick,
            onSaveMemberClick = onSaveMemberClick,
        )
    }

    SnackbarHost(
        hostState = snackHostState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}

@Preview
@Composable
fun previewGroupEditContent() {
    SharedBillTheme {
        Scaffold {
            GroupEditContent(
                state = State.Editing(Group.fake())
            )
        }
    }
}