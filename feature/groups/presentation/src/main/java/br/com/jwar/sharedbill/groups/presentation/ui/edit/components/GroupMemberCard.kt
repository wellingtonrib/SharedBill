package br.com.jwar.sharedbill.groups.presentation.ui.edit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissBackground
import br.com.jwar.sharedbill.core.designsystem.components.swipeToDismissConfirm
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissDeleteAction
import br.com.jwar.sharedbill.core.designsystem.components.UserAvatar
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.core.designsystem.theme.sizeMedium
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel

@Composable
fun GroupMemberCard(
    member: GroupMemberUiModel,
    onMemberSelect: (member: GroupMemberUiModel) -> Unit = {},
    onMemberDelete: (userId: String) -> Unit = {}
) {
    val swipeToDismissAction = SwipeToDismissDeleteAction(action = { onMemberDelete(member.uid) })
    val swipeToDismissState = swipeToDismissConfirm(
        onConfirm = { swipeToDismissAction.action.invoke() }
    )

    SwipeToDismiss(
        state = swipeToDismissState,
        background = {
            SwipeToDismissBackground(
                action = swipeToDismissAction,
                dismissState = swipeToDismissState
            )
        }
    ) {
        Card(
            onClick = { onMemberSelect(member) }
        ) {
            Row(
                modifier = Modifier.fillMaxWidthPaddingMedium(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .sizeMedium()
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    UserAvatar(user = member.toUserUiModel())
                }
                HorizontalSpacerMedium()
                Column {
                    Text(
                        text = member.name,
                        style = AppTheme.typo.titleMedium
                    )
                    if (member.inviteCode.isNullOrBlank().not()) {
                        Text(
                            text = stringResource(R.string.message_has_not_joined),
                            style = AppTheme.typo.bodySmall,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupMemberCard() {
    MaterialTheme {
        GroupMemberCard(GroupMemberUiModel.sample().copy(inviteCode = "123"))
    }
}
