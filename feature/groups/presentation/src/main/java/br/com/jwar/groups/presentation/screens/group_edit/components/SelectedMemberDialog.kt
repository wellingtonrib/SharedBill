package br.com.jwar.groups.presentation.screens.group_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.components.UserCard
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium

@Composable
fun SelectedMemberDialog(
    selectedMember: br.com.jwar.groups.presentation.models.GroupMemberUiModel? = null,
    onMemberSelectionChange: (br.com.jwar.groups.presentation.models.GroupMemberUiModel?) -> Unit = {}
) {
    selectedMember?.let { user ->
        Dialog(
            onDismissRequest = { onMemberSelectionChange(null) },
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidthPaddingMedium(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UserCard(user = user.toUserCardUiModel())
                    Text(text = user.getJoinInfo())
                    Button(onClick = { onMemberSelectionChange(null) }) {
                        Text(text = stringResource(R.string.label_ok))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedMemberDialog() {
    SharedBillTheme {
        SelectedMemberDialog(
            selectedMember = br.com.jwar.groups.presentation.models.GroupMemberUiModel.sample()
        )
    }
}