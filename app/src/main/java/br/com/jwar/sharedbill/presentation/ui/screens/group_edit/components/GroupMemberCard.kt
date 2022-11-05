package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.presentation.ui.theme.horizontalSpaceMedium
import br.com.jwar.sharedbill.presentation.ui.theme.sizeMedium
import kotlinx.coroutines.launch

@Composable
fun GroupMemberCard(
    member: UserUiModel,
    onMemberSelect: (member: UserUiModel) -> Unit = {},
    onMemberDelete: (userId: String) -> Unit = {}
) {
    val userDeletionState = GetUserDeletionState(member, onMemberDelete)

    SwipeToDismiss(
        state = userDeletionState,
        background = {}
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
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_person_24),
                        contentDescription = stringResource(R.string.description_group_image),
                        colorFilter = ColorFilter.tint(color = AppTheme.colors.onPrimary),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.horizontalSpaceMedium())
                Text(text = member.name, style = AppTheme.typo.titleMedium)
            }
        }
    }
}

@Composable
private fun GetUserDeletionState(
    member: UserUiModel,
    onMemberDelete: (userId: String) -> Unit
): DismissState {
    val scope = rememberCoroutineScope()
    val confirmUserDeletion = remember { mutableStateOf(false) }
    val userDeletionDismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                confirmUserDeletion.value = true
            }
            true
        }
    )

    if (confirmUserDeletion.value) {
        AlertDialog(
            onDismissRequest = { confirmUserDeletion.value = false },
            title = { Text(stringResource(R.string.label_confirm)) },
            text = { Text(stringResource(R.string.message_confirm_member_deletion)) },
            confirmButton = {
                Button(
                    onClick = {
                        onMemberDelete(member.uid)
                        confirmUserDeletion.value = false
                    }
                ) {
                    Text(stringResource(R.string.label_yes))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        scope.launch { userDeletionDismissState.reset() }
                        confirmUserDeletion.value = false
                    }
                ) {
                    Text(stringResource(R.string.label_cancel))
                }
            }
        )
    }
    return userDeletionDismissState
}

@Preview(showBackground = true)
@Composable
fun GroupMemberCard() {
    MaterialTheme {
        GroupMemberCard(UserUiModel.sample())
    }
}

