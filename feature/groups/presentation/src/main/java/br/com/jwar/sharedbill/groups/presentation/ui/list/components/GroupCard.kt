package br.com.jwar.sharedbill.groups.presentation.ui.list.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.DismissDirection
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissBackground
import br.com.jwar.sharedbill.core.designsystem.components.swipeToDismissConfirm
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissDeleteAction
import br.com.jwar.sharedbill.core.designsystem.components.SwipeToDismissLeaveAction
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingLarge
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun GroupCard(
    group: GroupUiModel,
    onGroupClick: (groupId: String) -> Unit = {},
    onGroupDelete: (groupId: String) -> Unit = {},
    onGroupLeave: (groupId: String) -> Unit = {},
) {
    val swipeToDismissAction =
        if (group.isCurrentUserOwner) {
            SwipeToDismissDeleteAction(action = { onGroupDelete(group.id) })
        } else {
            SwipeToDismissLeaveAction(action = { onGroupLeave(group.id) })
        }

    val swipeToDismissState = swipeToDismissConfirm(
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
                Column(
                    modifier = Modifier
                        .paddingMedium()
                        .weight(1f)
                ) {
                    Text(
                        text = group.title.ifEmpty { stringResource(R.string.label_unnamed) },
                        style = AppTheme.typo.titleMedium
                    )
                    Spacer(modifier = Modifier.height(AppTheme.dimens.space_2))
                    Text(
                        text = stringResource(R.string.label_total_spent, group.total),
                        style = AppTheme.typo.bodyMedium
                    )
                }
                Icon(
                    modifier = Modifier.paddingMedium(),
                    imageVector = ImageVector.vectorResource(DSR.drawable.ic_chevron_right),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewGroupsListItem() {
    SharedBillTheme {
        Box(
            modifier = Modifier.paddingLarge()
        ) {
            GroupCard(group = GroupUiModel.sample())
        }
    }
}
