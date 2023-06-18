package br.com.jwar.groups.presentation.screens.group_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DismissDirection
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissBackground
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissConfirm
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissDeleteAction
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissLeaveAction
import br.com.jwar.sharedbill.core.designsystem.components.UserAvatarStack
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupListItem(
    group: GroupUiModel,
    onGroupClick: (groupId: String) -> Unit = {},
    onGroupDelete: (groupId: String) -> Unit = {},
    onGroupLeave: (groupId: String) -> Unit = {},
) {
    val swipeToDismissAction =
        if (group.isCurrentUserOwner)
            SwipeToDismissDeleteAction(action = { onGroupDelete(group.id) })
        else SwipeToDismissLeaveAction(action = { onGroupLeave(group.id) })

    val swipeToDismissState = SwipeToDismissConfirm(
        onConfirm = { swipeToDismissAction.action.invoke() }
    )

    SwipeToDismiss(
        state = swipeToDismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            SwipeToDismissBackground(
                action = swipeToDismissAction,
                dismissState = swipeToDismissState
            )
        }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onGroupClick(group.id) },
        ) {
            Column(
                modifier = Modifier.fillMaxWidthPaddingMedium()
            ) {
                Text(
                    text = group.title.ifEmpty { stringResource(id = R.string.label_unnamed) },
                    style = AppTheme.typo.titleLarge
                )
                VerticalSpacerSmall()
                UserAvatarStack(
                    users = group.members.map { it.toUserUiModel() },
                    avatarSize = 42.dp,
                    overlap = 12.dp
                )
                VerticalSpacerSmall()
                Text(
                    text = pluralStringResource(R.plurals.label_group_members_count, group.members.size, group.members.size),
                    style = AppTheme.typo.titleSmall
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewGroupsListItem() {
    SharedBillTheme {
        GroupListItem(group = GroupUiModel.sample())
    }
}