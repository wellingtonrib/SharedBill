package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.generic_components.InfoDialog
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditContract
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun SelectedMemberDialog(
    state: GroupEditContract.State.Editing,
    onMemberSelectionChange: (UserUiModel?) -> Unit = {}
) {
    state.selectedMember?.let { user ->
        InfoDialog(
            image = R.drawable.ic_baseline_account_circle_24,
            title = user.name,
            message = user.getJoinInfo(),
            action = stringResource(R.string.label_ok),
            onDismiss = { onMemberSelectionChange(null) },
            onAction = { onMemberSelectionChange(null) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedMemberDialog() {
    SharedBillTheme {
        SelectedMemberDialog(
            state = GroupEditContract.State.Editing(
                group = GroupUiModel.sample(),
                selectedMember = UserUiModel.sample()
            )
        )
    }
}