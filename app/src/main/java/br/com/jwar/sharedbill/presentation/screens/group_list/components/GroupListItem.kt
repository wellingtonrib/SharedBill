package br.com.jwar.sharedbill.presentation.screens.group_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.DismissDirection
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissBackground
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissConfirm
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissDeleteAction
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissLeaveAction
import br.com.jwar.sharedbill.core.designsystem.theme.*
import br.com.jwar.sharedbill.presentation.models.GroupUiModel

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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .sizeLarge()
                        .background(AppTheme.colors.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .sizeMedium(),
                        imageVector = ImageVector.vectorResource(Icons.Groups),
                        contentDescription = stringResource(R.string.description_group_image),
                        colorFilter = ColorFilter.tint(color = AppTheme.colors.onPrimary),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier.paddingMedium()
                ) {
                    Text(text = group.title.ifEmpty { stringResource(id = R.string.label_unnamed) }, style = AppTheme.typo.titleMedium)
                    Text(text = pluralStringResource(R.plurals.label_group_members_count, group.members.size, group.members.size), style = AppTheme.typo.titleSmall)
                }
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