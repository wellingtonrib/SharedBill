package br.com.jwar.groups.presentation.screens.group_edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.groups.presentation.screens.group_edit.GroupEditContract.State
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun GroupEditScreen(
    state: State,
    snackHostState: SnackbarHostState = SnackbarHostState(),
    onGroupUpdated: (GroupUiModel) -> Unit = {},
    onSaveMemberClick: (String) -> Unit = {},
    onMemberSelectionChange: (br.com.jwar.groups.presentation.models.GroupMemberUiModel?) -> Unit = {},
    onMemberDeleteClick: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    when {
        state.isLoading -> LoadingContent()
        state.uiModel != null -> GroupEditForm(
            group = state.uiModel,
            selectedMember = state.selectedMember,
            onGroupUpdated = onGroupUpdated,
            onSaveMemberClick = onSaveMemberClick,
            onMemberSelectionChange = onMemberSelectionChange,
            onMemberDeleteClick = onMemberDeleteClick,
            onSaveClick = onSaveClick,
            onNavigateBack = onNavigateBack
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
            GroupEditScreen(
                state = State(uiModel = GroupUiModel.sample()),
            )
        }
    }
}