package br.com.jwar.sharedbill.groups.presentation.ui.group_edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.presentation.ui.group_edit.GroupEditContract.State
import br.com.jwar.sharedbill.groups.presentation.ui.group_edit.components.GroupEditContent
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel

@Composable
fun GroupEditScreen(
    state: State,
    snackHostState: SnackbarHostState = SnackbarHostState(),
    onGroupUpdated: (GroupUiModel) -> Unit = {},
    onSaveMemberClick: (String) -> Unit = {},
    onMemberSelectionChange: (GroupMemberUiModel?) -> Unit = {},
    onMemberDeleteClick: (String) -> Unit = {},
    onShareInviteCodeClick: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    when {
        state.isLoading -> LoadingContent()
        state.uiModel != null -> GroupEditContent(
            group = state.uiModel,
            selectedMember = state.selectedMember,
            onGroupUpdated = onGroupUpdated,
            onSaveMemberClick = onSaveMemberClick,
            onMemberSelectionChange = onMemberSelectionChange,
            onMemberDeleteClick = onMemberDeleteClick,
            onSaveClick = onSaveClick,
            onShareInviteCodeClick = onShareInviteCodeClick,
            onNavigateBack = onNavigateBack
        )
    }

    SnackbarHost(
        hostState = snackHostState,
        modifier = Modifier.fillMaxWidth().wrapContentHeight(Alignment.Bottom)
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewGroupEditContent() {
    SharedBillTheme {
        Scaffold {
            GroupEditScreen(
                state = State(uiModel = GroupUiModel.sample()),
            )
        }
    }
}