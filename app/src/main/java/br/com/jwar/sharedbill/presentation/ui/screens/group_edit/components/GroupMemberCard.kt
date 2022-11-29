package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.generic_components.SwipeToDismissBackground
import br.com.jwar.sharedbill.presentation.ui.generic_components.SwipeToDismissConfirm
import br.com.jwar.sharedbill.presentation.ui.generic_components.SwipeToDismissLeaveAction
import br.com.jwar.sharedbill.presentation.ui.screens.account.components.UserImage
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.presentation.ui.theme.sizeMedium

@Composable
fun GroupMemberCard(
    member: UserUiModel,
    onMemberSelect: (member: UserUiModel) -> Unit = {},
    onMemberDelete: (userId: String) -> Unit = {}
) {
    val swipeToDismissAction = SwipeToDismissLeaveAction(action = { onMemberDelete(member.uid) })
    val swipeToDismissState = SwipeToDismissConfirm(
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
                        .clip(CircleShape)
                        .background(AppTheme.colors.primary),
                    contentAlignment = Alignment.Center
                ) {
                    UserImage(member.imageUrl, bordered = false)
                }
                HorizontalSpacerMedium()
                Text(text = member.name, style = AppTheme.typo.titleMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupMemberCard() {
    MaterialTheme {
        GroupMemberCard(UserUiModel.sample())
    }
}

