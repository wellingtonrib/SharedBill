package br.com.jwar.groups.presentation.ui.group_edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.core.designsystem.components.UserCard
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun SelectedMemberDialog(
    selectedMember: GroupMemberUiModel? = null,
    onMemberSelectionChange: (GroupMemberUiModel?) -> Unit = {},
    onShareInviteCodeClick: (String) -> Unit = {}
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
                    Icon(
                        Icons.Rounded.Close,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable {
                                onMemberSelectionChange(null)
                            },
                        contentDescription = stringResource(R.string.description_close)
                    )
                    UserCard(user = user.toUserUiModel())
                    VerticalSpacerSmall()
                    JoinInfo(user, onShareInviteCodeClick)
                }
            }
        }
    }
}

@Composable
private fun JoinInfo(
    user: GroupMemberUiModel,
    onShareInviteCodeClick: (String) -> Unit
) {
    if (user.inviteCode.isNullOrBlank()) {
        Text(text = stringResource(R.string.label_joined))
    } else {
        Text(text = stringResource(R.string.message_invite_code, user.inviteCode))
        VerticalSpacerSmall()
        Button(onClick = {
            onShareInviteCodeClick(user.inviteCode)
        }) {
            Text(text = stringResource(R.string.label_share_invite_code))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedMemberDialog() {
    SharedBillTheme {
        SelectedMemberDialog(
            selectedMember = GroupMemberUiModel.sample().copy(inviteCode = "123")
        )
    }
}